package ltd.scau.search.crawler.mq;

import org.apache.rocketmq.client.consumer.DefaultMQPullConsumer;
import org.apache.rocketmq.client.consumer.PullResult;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.message.MessageQueue;
import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Weijie Wu
 */
public class PullConsumerTest {

    private static final Map<MessageQueue, Long> OFFSE_TABLE = new HashMap<>();

    @Test
    public void consume() throws Exception {
        DefaultMQPullConsumer consumer = new DefaultMQPullConsumer("crawlerGroup");
        consumer.setNamesrvAddr("localhost:9876");

        consumer.start();

        Set<String> uris = new HashSet<>();

        Set<MessageQueue> mqs = consumer.fetchSubscribeMessageQueues("uri");
        for (MessageQueue mq : mqs) {
            System.out.printf("Consume from the queue: %s%n", mq);
            SINGLE_MQ:
            while (true) {
                try {
                    PullResult pullResult =
                            consumer.pull(mq, null, getMessageQueueOffset(mq), 32, 1000);
                    System.out.printf("%s%n", pullResult);
                    putMessageQueueOffset(mq, pullResult.getNextBeginOffset());
                    switch (pullResult.getPullStatus()) {
                        case FOUND:
                            List<MessageExt> msg = pullResult.getMsgFoundList();
//                            msg.stream()
//                            System.out.println(msg.stream().map(m -> m.getQueueId() + " -> " + m.getQueueOffset() + ": " + m.getSysFlag()).collect(Collectors.toList()));
//                            System.out.println(msg.get(0));
//                            consumer.updateConsumeOffset(mq, pullResult.getNextBeginOffset());
                            break;
                        case NO_MATCHED_MSG:
                            break;
                        case NO_NEW_MSG:
                            break SINGLE_MQ;
                        case OFFSET_ILLEGAL:
                            break;
                        default:
                            break;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        consumer.shutdown();
    }

    private static long getMessageQueueOffset(MessageQueue mq) {
        Long offset = OFFSE_TABLE.get(mq);
        if (offset != null)
            return offset;

        return 0;
    }

    private static void putMessageQueueOffset(MessageQueue mq, long offset) {
        OFFSE_TABLE.put(mq, offset);
    }
}

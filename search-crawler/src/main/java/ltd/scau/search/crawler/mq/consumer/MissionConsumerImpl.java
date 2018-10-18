package ltd.scau.search.crawler.mq.consumer;

import com.alibaba.fastjson.JSONObject;
import ltd.scau.search.crawler.entity.ExecuteResult;
import ltd.scau.search.commons.entity.Mission;
import ltd.scau.search.crawler.entity.CrawledPage;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Function;

/**
 * @author Weijie Wu
 */
@Component
public class MissionConsumerImpl implements MissionConsumer {

    private static final Log logger = LogFactory.getLog(MissionConsumerImpl.class);

    private DefaultMQPushConsumer defaultMQPushConsumer;

    @Value("${default.charset}")
    private String defaultCharset;

    @Value("${rocketmq.namesrvAddr}")
    private String namesrvAddr;

    @Value("${rocketmq.consumerGroup}")
    private String consumerGroup;

    @Value("${rocketmq.consume.threadMin}")
    private Integer threadMin;

    @Value("${rocketmq.consume.threadMax}")
    private Integer threadMax;

    @Value("${rocketmq.topic}")
    private String topic;

    @Value("${rocketmq.subexpression}")
    private String subExpression;

    @Override
    public void onMission(Function<Mission, ExecuteResult<CrawledPage>> messageHandler) throws MQClientException {
        defaultMQPushConsumer = new DefaultMQPushConsumer(consumerGroup);
        defaultMQPushConsumer.setNamesrvAddr(namesrvAddr);
        defaultMQPushConsumer.subscribe(topic, subExpression);
        defaultMQPushConsumer.setConsumeThreadMin(threadMin);
        defaultMQPushConsumer.setConsumeThreadMax(threadMax);
        defaultMQPushConsumer.registerMessageListener((MessageListenerConcurrently) (msgs, context) -> {

            int ackIndex = context.getAckIndex();
            logger.debug("ACK >> " + ackIndex);

            AtomicBoolean error = new AtomicBoolean(false);
            msgs.forEach(m -> {
                Mission mission = null;
                try {
                    mission = JSONObject.parseObject(new String(m.getBody(), defaultCharset), Mission.class);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    error.set(true);
                    return;
                }
                ExecuteResult<CrawledPage> result = messageHandler.apply(mission);

                if (!result.getSucceed()) {
                    error.set(true);
                    return;
                }
            });
            return error.get() ? ConsumeConcurrentlyStatus.RECONSUME_LATER : ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        });

        defaultMQPushConsumer.start();
        logger.info("Consumer started.");
    }
}

package ltd.scau.search.crawler.mq.consumer;

import com.alibaba.fastjson.JSONObject;
import ltd.scau.search.crawler.entity.ExecuteResult;
import ltd.scau.search.crawler.entity.Mission;
import ltd.scau.search.crawler.entity.CrawledPage;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private DefaultMQPushConsumer defaultMQPushConsumer;

    @Value("${default.charset}")
    private String defaultCharset;

    @Override
    public void onMission(Function<Mission, ExecuteResult<CrawledPage>> messageHandler) throws MQClientException {

        defaultMQPushConsumer.registerMessageListener((MessageListenerConcurrently) (msgs, context) -> {
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

package ltd.scau.search.crawler.mq;

import ltd.scau.search.crawler.component.Crawler;
import ltd.scau.search.crawler.component.CrawlerImpl;
import ltd.scau.search.crawler.entity.Page;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author Weijie Wu
 */
@Component
public class MissionConsumer {

    private static final Log logger = LogFactory.getLog(MissionConsumer.class);

    @Autowired
    private DefaultMQPushConsumer defaultMQPushConsumer;

    @Autowired
    private Crawler crawler;

    public void start() throws MQClientException {
        defaultMQPushConsumer.registerMessageListener((MessageListenerConcurrently) (msgs, context) -> {
            msgs.forEach(m -> {
                String uri = new String(m.getBody());
                Page page = null;
                try {
                    page = crawler.crawl(uri);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                logger.debug(page);
            });
            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        });
        defaultMQPushConsumer.start();
        logger.info("Consumer started.");
    }
}

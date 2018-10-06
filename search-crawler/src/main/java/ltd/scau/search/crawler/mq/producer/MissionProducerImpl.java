package ltd.scau.search.crawler.mq.producer;

import com.alibaba.fastjson.JSON;
import ltd.scau.search.commons.entity.Mission;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author Weijie Wu
 */
@Component
public class MissionProducerImpl implements MissionProducer {

    @Autowired
    private DefaultMQProducer defaultMQProducer;

    @Value("${rocketmq.topic}")
    private String topic;

    @Value("${rocketmq.tags}")
    private String tags;

    @Value("${default.charset}")
    private String defaultCharset;

    @Override
    public void submit(Mission... mission) throws Exception {
        for (int i = 0; i < mission.length; i++) {
            Message message = new Message(topic, tags, JSON.toJSONString(mission[i]).getBytes(defaultCharset));
            defaultMQProducer.send(message);
        }
    }
}

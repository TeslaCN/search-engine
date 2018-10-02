package ltd.scau.search.crawler.mq;

import com.alibaba.fastjson.JSONObject;
import ltd.scau.search.crawler.entity.Mission;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.net.URI;
import java.util.Date;

/**
 * @author Weijie Wu
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ProducerTest {

    @Autowired
    private DefaultMQProducer defaultMQProducer;

    @Test
    public void test() throws Exception {
        // Specify name server addresses.
        //Launch the instance.
        defaultMQProducer.start();
        for (int i = 0; i < 10; i++) {
            //Create a message instance, specifying topic, tag and message body.
            Message msg = new Message("uri" /* Topic */,
                    "TagA" /* Tag */,
                    ("http://scau.ltd/" +
                            i + "/" + new Date()).getBytes(RemotingHelper.DEFAULT_CHARSET) /* Message body */
            );
            //Call send message to deliver message to one of brokers.
            SendResult sendResult = defaultMQProducer.send(msg);
            System.out.printf("%s%n", sendResult);
            Thread.sleep(100);
        }
        //Shut down once the producer instance is not longer in use.
//        defaultMQProducer.shutdown();
    }

    @Test
    public void testSingle() throws Exception {
        // Specify name server addresses.
        //Launch the instance.
//        defaultMQProducer.start();

        Mission mission = Mission.create(URI.create("http://info.scau.edu.cn"));
        //Create a message instance, specifying topic, tag and message body.
//        Message msg = new Message("uri", "default", "http://info.scau.edu.cn/".getBytes(RemotingHelper.DEFAULT_CHARSET));
        Message msg = new Message("uri", "default", JSONObject.toJSONString(mission).getBytes(RemotingHelper.DEFAULT_CHARSET));

        //Call send message to deliver message to one of brokers.
        SendResult sendResult = defaultMQProducer.send(msg);
        System.out.printf("%s%n", sendResult);

        //Shut down once the producer instance is not longer in use.
//        defaultMQProducer.shutdown();
    }
}

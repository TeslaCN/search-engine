package ltd.scau.search.crawler.mq.producer;

import ltd.scau.search.commons.entity.Mission;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.net.URI;

/**
 * @author Weijie Wu
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class MissionProducerImplTest {

    @Autowired
    private MissionProducer missionProducer;

    @Test
    public void produce() throws Exception {
        Mission[] missions = new Mission[] {
                Mission.create(URI.create("http://info.scau.edu.cn/news-3394.html")),
        };
        missionProducer.submit(missions);
    }

}
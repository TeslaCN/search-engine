package ltd.scau.search.crawler.component;

import ltd.scau.search.crawler.component.crawler.Crawler;
import ltd.scau.search.crawler.entity.ExecuteResult;
import ltd.scau.search.crawler.entity.Mission;
import ltd.scau.search.crawler.entity.CrawledPage;
import ltd.scau.search.crawler.mq.consumer.MissionConsumer;
import ltd.scau.search.crawler.mq.producer.MissionProducer;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.rocketmq.client.exception.MQClientException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author Weijie Wu
 */
@Component
public class MissionExecutor {

    private static final Log logger = LogFactory.getLog(MissionExecutor.class);

    @Autowired
    private MissionConsumer missionConsumer;

    @Autowired
    private MissionProducer missionProducer;

    @Autowired
    private Crawler crawler;

    public void start() throws MQClientException {
        missionConsumer.onMission(mission -> {
            CrawledPage page = null;
            try {
                page = crawler.execute(mission);
            } catch (IOException e) {
                e.printStackTrace();
                return ExecuteResult.aResult().succeed(false).message(e.getMessage()).build();
            }

            try {
                missionProducer.submit(page.getHrefs().stream().map(Mission::create).toArray(Mission[]::new));
            } catch (Exception e) {
                e.printStackTrace();
            }

            return ExecuteResult.aResult().succeed(true).product(page).build();
        });
    }
}

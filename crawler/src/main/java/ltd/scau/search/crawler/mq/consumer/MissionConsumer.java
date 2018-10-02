package ltd.scau.search.crawler.mq.consumer;

import ltd.scau.search.crawler.entity.ExecuteResult;
import ltd.scau.search.crawler.entity.Mission;
import ltd.scau.search.crawler.entity.CrawledPage;
import org.apache.rocketmq.client.exception.MQClientException;

import java.util.function.Function;

/**
 * @author Weijie Wu
 */
public interface MissionConsumer {

    void onMission(Function<Mission, ExecuteResult<CrawledPage>> messageHandler) throws MQClientException;

}

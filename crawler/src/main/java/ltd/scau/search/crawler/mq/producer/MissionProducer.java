package ltd.scau.search.crawler.mq.producer;

import ltd.scau.search.crawler.entity.Mission;

/**
 * @author Weijie Wu
 */
public interface MissionProducer {

    void submit(Mission... mission) throws Exception;

}

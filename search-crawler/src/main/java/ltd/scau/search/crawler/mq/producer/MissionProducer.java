package ltd.scau.search.crawler.mq.producer;

import ltd.scau.search.commons.entity.Mission;

/**
 * @author Weijie Wu
 */
public interface MissionProducer {

    void submit(Mission... mission) throws Exception;

}

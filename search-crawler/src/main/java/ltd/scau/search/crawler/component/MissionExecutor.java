package ltd.scau.search.crawler.component;

import ltd.scau.search.commons.entity.es.PageEsEntity;
import ltd.scau.search.commons.entity.mongodb.PageMongoEntity;
import ltd.scau.search.commons.service.es.PageESRepository;
import ltd.scau.search.commons.service.mongodb.PageMongoRepository;
import ltd.scau.search.crawler.component.crawler.Crawler;
import ltd.scau.search.crawler.entity.CrawledPage;
import ltd.scau.search.crawler.entity.ExecuteResult;
import ltd.scau.search.commons.entity.Mission;
import ltd.scau.search.crawler.mq.consumer.MissionConsumer;
import ltd.scau.search.commons.mq.producer.MissionProducer;
import ltd.scau.search.crawler.service.redis.RedisDao;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.rocketmq.client.exception.MQClientException;
import org.dom4j.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

    @Autowired
    private PageESRepository pageESRepository;

    @Autowired
    private PageMongoRepository pageMongoRepository;

    @Autowired
    private RedisDao redisDao;

    @Value("${crawled.redis.set.name}")
    private String crawledRedisSetName;

    public void start() throws MQClientException {
        missionConsumer.onMission(mission -> {
            String uriMd5 = DigestUtils.md5Hex(mission.getUri().toString());

            if (redisDao.setContains(crawledRedisSetName, uriMd5)) {
                return ExecuteResult.aResult().succeed(true).build();
            }
            CrawledPage page = null;
            try {
                page = crawler.execute(mission);
                if (page == null) {
                    return ExecuteResult.aResult().succeed(true).build();
                }
            } catch (IOException | DocumentException e) {
                e.printStackTrace();
                return ExecuteResult.aResult().succeed(false).message(e.getMessage()).build();
            }

            logger.debug(page);

            PageMongoEntity mongoEntity = new PageMongoEntity();
            mongoEntity.setUri(mission.getUri().toString());
            mongoEntity.setCode(page.getCode());
            mongoEntity.setTimestamp(page.getTime().getTime());
            mongoEntity.setTitle(page.getTitle());
            mongoEntity.setHtml(page.getHtml());
            pageMongoRepository.save(mongoEntity);

            PageEsEntity esEntity = new PageEsEntity();
            esEntity.setUri(mission.getUri().toString());
            esEntity.setCode(page.getCode());
            esEntity.setCrawlDate(page.getTime().getTime());
            esEntity.setTitle(page.getTitle());
            esEntity.setContent(page.getContent());
            pageESRepository.index(esEntity);

            redisDao.setAdd(crawledRedisSetName, uriMd5);
            try {
                Mission[] missions = page.getHrefs().stream()
                        .filter(uri -> uri.getHost() != null
                                && uri.getHost().equals(mission.getUri().getHost())
                                && !redisDao.setContains(crawledRedisSetName, DigestUtils.md5Hex(uri.toString()))
                        ).map(Mission::create).toArray(Mission[]::new);
                missionProducer.submit(missions);
            } catch (Exception e) {
                e.printStackTrace();
            }

            return ExecuteResult.aResult().succeed(true).product(page).build();
        });
    }
}

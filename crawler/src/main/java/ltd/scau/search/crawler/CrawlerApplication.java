package ltd.scau.search.crawler;

import ltd.scau.search.crawler.mq.MissionConsumer;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CrawlerApplication implements CommandLineRunner {

    private static final Log logger = LogFactory.getLog(CrawlerApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(CrawlerApplication.class, args);
    }

    @Autowired
    private MissionConsumer missionConsumer;

    @Override
    public void run(String... args) throws Exception {
        logger.info("hello, world -> INFO");
        logger.debug("hello, world -> DEBUG");
        logger.error("hello, world -> ERROR");
        missionConsumer.start();
    }
}

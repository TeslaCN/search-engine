package ltd.scau.search;

import ltd.scau.search.crawler.component.MissionExecutor;
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
        System.setProperty("es.set.netty.runtime.available.processors", "false");
        SpringApplication.run(CrawlerApplication.class, args);
    }

    @Autowired
    private MissionExecutor missionConsumer;

    @Override
    public void run(String... args) throws Exception {
        logger.info("hello, world -> INFO");
        logger.warn("hello, world -> WARN");
        logger.debug("hello, world -> DEBUG");
        logger.error("hello, world -> ERROR");
        missionConsumer.start();
    }
}

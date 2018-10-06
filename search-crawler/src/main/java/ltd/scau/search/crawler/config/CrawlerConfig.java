package ltd.scau.search.crawler.config;

import ltd.scau.search.crawler.component.crawler.Crawler;
import ltd.scau.search.crawler.component.crawler.CrawlerImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Weijie Wu
 */
@Configuration
public class CrawlerConfig {

    @Bean
    public Crawler crawler() {
        return new CrawlerImpl();
    }
}

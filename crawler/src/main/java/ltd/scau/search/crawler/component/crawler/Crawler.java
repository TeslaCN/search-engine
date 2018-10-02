package ltd.scau.search.crawler.component.crawler;

import ltd.scau.search.crawler.entity.Mission;
import ltd.scau.search.crawler.entity.CrawledPage;

import java.io.IOException;
import java.net.URI;

/**
 * @author Weijie Wu
 */
public interface Crawler {

    CrawledPage crawl(URI uri) throws IOException;

    default CrawledPage crawl(String uri) throws IOException {
        return crawl(URI.create(uri));
    }

    default CrawledPage execute(Mission mission) throws IOException {
        return crawl(mission.getUri());
    }
}

package ltd.scau.search.crawler.component.crawler;

import ltd.scau.search.commons.entity.Mission;
import ltd.scau.search.crawler.entity.CrawledPage;
import org.dom4j.DocumentException;

import java.io.IOException;
import java.net.URI;

/**
 * @author Weijie Wu
 */
public interface Crawler {

    CrawledPage crawl(URI uri) throws IOException, DocumentException;

    default CrawledPage crawl(String uri) throws IOException, DocumentException {
        return crawl(URI.create(uri));
    }

    default CrawledPage execute(Mission mission) throws IOException, DocumentException {
        return crawl(mission.getUri());
    }
}

package ltd.scau.search.crawler.component;

import ltd.scau.search.crawler.entity.Page;

import java.io.IOException;
import java.net.URI;

/**
 * @author Weijie Wu
 */
public interface Crawler {

    Page crawl(URI uri) throws IOException;

    default Page crawl(String uri) throws IOException {
        return crawl(URI.create(uri));
    }
}

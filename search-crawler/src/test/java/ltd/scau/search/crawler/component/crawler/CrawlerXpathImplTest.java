package ltd.scau.search.crawler.component.crawler;

import ltd.scau.search.crawler.entity.CrawledPage;
import org.dom4j.DocumentException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.net.URI;

import static org.junit.Assert.*;

/**
 * @author Weijie Wu
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class CrawlerXpathImplTest {

    @Autowired
    private Crawler crawler;

    @Test
    public void crawl() throws IOException, DocumentException {
        String[] uris = {"http://info.scau.edu.cn/news-3390.html"};
        for (String s : uris) {
            CrawledPage page = crawler.crawl(URI.create(s));
            System.out.println(page.getUri());
            System.out.println(page.getTitle());
            System.out.println(page.getContent());
        }
    }
}
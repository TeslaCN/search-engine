package ltd.scau.search.crawler.component.crawler;

import ltd.scau.search.crawler.entity.CrawledPage;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.nio.charset.Charset;
import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Weijie Wu
 */
@Component
public class CrawlerImpl implements Crawler {

    @Autowired
    private HttpClient httpClient;

    @Autowired
    private Charset defaultCharset;

    @Override
    public CrawledPage crawl(URI uri) throws IOException {

        HttpGet httpGet = new HttpGet(uri);
        HttpResponse response = httpClient.execute(httpGet);

        StatusLine statusLine = response.getStatusLine();
        int code = statusLine.getStatusCode();

        HttpEntity entity = response.getEntity();
        String html = EntityUtils.toString(entity, defaultCharset);

        Document document = Jsoup.parse(html, uri.toString());
        Elements a = document.getElementsByTag("a");
        Set<String> hrefs = a.stream().map(element -> element.attr("href")).filter(s -> s.charAt(0) != '#').collect(Collectors.toSet());

        return CrawledPage.newPage(uri).code(code).html(html).content(document.text()).hrefs(hrefs).when(new Date()).build();
    }
}

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
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Weijie Wu
 * @deprecated 爬虫简单实现，该实现仅为了第一版快速成型，以后不再使用
 */
//@Component
@Deprecated
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

        List<String> titles = document.getElementsByTag("h1").eachText();
        if (titles == null || titles.isEmpty()) {
            titles = document.getElementsByTag("h2").eachText();
        }

        return CrawledPage.newPage(uri).code(code).title(titles).html(html).content(Arrays.asList(document.text())).hrefs(hrefs).when(new Date()).build();
    }
}

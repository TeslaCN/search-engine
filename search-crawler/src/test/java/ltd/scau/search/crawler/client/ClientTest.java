package ltd.scau.search.crawler.client;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Weijie Wu
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class ClientTest {

    @Autowired
    private HttpClient client;

    @Test
    public void scauInfo() throws IOException, URISyntaxException {
        URI uri = new URI("http://info.scau.edu.cn");
        HttpGet get = new HttpGet(uri);
        HttpResponse response = client.execute(get);
        HttpEntity entity = response.getEntity();
        String html = EntityUtils.toString(entity, Charset.forName("utf-8"));
        System.out.println(html);
        Document document = Jsoup.parse(html);
        Elements elements = document.getElementsByTag("a");
        List<String> href = elements.stream().map(element -> element.attr("href")).filter(s -> !s.equals("#")).collect(Collectors.toList());
        href.forEach(h -> System.out.println(h + " -> " + uri.resolve(h)));
        System.out.println();
    }

    @Test
    public void scauLtd() throws IOException, URISyntaxException {
        URI uri = new URI("https://scau.ltd");
        HttpGet get = new HttpGet(uri);
        HttpResponse response = client.execute(get);
        HttpEntity entity = response.getEntity();
        String html = EntityUtils.toString(entity, Charset.forName("utf-8"));
        System.out.println(html);
        Document document = Jsoup.parse(html);
        Elements elements = document.getElementsByTag("a");
        List<String> href = elements.stream().map(element -> element.attr("href")).filter(s -> !s.equals("#")).collect(Collectors.toList());
        href.forEach(h -> System.out.println(h + " -> " + uri.resolve(h)));
        System.out.println();
    }

}

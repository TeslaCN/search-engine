package ltd.scau.search.crawler.client;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.client.LaxRedirectStrategy;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

/**
 * @author Weijie Wu
 */
public class CrawlTest {

    private HttpClient httpClient;

    @Before
    public void init() {
        httpClient = HttpClients.custom()
                .setDefaultCookieStore(new BasicCookieStore())
                .setRedirectStrategy(new LaxRedirectStrategy())
                .build();
    }

    @Test
    public void test() throws IOException {
        HttpGet get = new HttpGet("http://info.scau.edu.cn/upload/ueditor/file/20180927_1538049061365009295.xls");
        HttpResponse response = httpClient.execute(get);
        System.out.println(response.getLocale());
        System.out.println(response.getStatusLine());
        System.out.println(response);
        HttpEntity entity = response.getEntity();
        System.out.println(entity);
        ContentType contentType = ContentType.parse(entity.getContentType().getValue());
        System.out.println("Content-Type => " + contentType);
        System.out.println("Content-Type.Mime => " + contentType.getMimeType());
    }
}

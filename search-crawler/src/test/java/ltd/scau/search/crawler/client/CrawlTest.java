package ltd.scau.search.crawler.client;

import ltd.scau.search.commons.util.URIs;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.client.LaxRedirectStrategy;
import org.apache.http.util.EntityUtils;
import org.dom4j.DocumentException;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.Charset;

import static ltd.scau.search.crawler.util.Htmls.getCharsetInMeta;

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
    public void test() throws IOException, DocumentException {
        String[] uris = {
                "http://info.scau.edu.cn",
                "http://wcce.scau.edu.cn",
        };
        for (String s : uris) {
            HttpGet get = new HttpGet(s);
            HttpResponse response = httpClient.execute(get);
            System.out.println(response.getLocale());
            System.out.println(response.getStatusLine());
            System.out.println(response);
            HttpEntity entity = response.getEntity();
            System.out.println(entity);
            ContentType contentType = ContentType.parse(entity.getContentType().getValue());
            System.out.println("Content-Type => " + contentType.getCharset());
            System.out.println("Content-Type.Mime => " + contentType.getMimeType());

            final Charset defaultCharset = Charset.forName("utf-8");

            byte[] bytes = EntityUtils.toByteArray(entity);

            String html = new String(bytes, defaultCharset);

            Charset charset = getCharsetInMeta(html);

            System.err.println(charset);

            html = new String(bytes, charset == null ? defaultCharset : charset);
            System.out.println(html);
        }
    }
}

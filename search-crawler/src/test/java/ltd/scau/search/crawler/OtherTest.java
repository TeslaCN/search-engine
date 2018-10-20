package ltd.scau.search.crawler;

import org.junit.Test;

import java.net.URI;
import java.util.Arrays;

/**
 * @author Weijie Wu
 */
public class OtherTest {

    @Test
    public void uri() {
        URI uri = URI.create("mailto:zhaopin@pci-suntektech.com");
        System.out.println(uri);
    }

    @Test
    public void resolve() {
        String[] strings = {
                "http://wcce.scau.edu.cn",
                "http://wcce.scau.edu.cn/",
                "http://wcce.scau.edu.cn/cncontent.asp?id=190",
        };
        for (String u : strings) {
            URI uri = URI.create(u);
            String[] uris = {"cncontent.asp?id=1", "/cncontent.asp?id=10", "/"};
            Arrays.stream(uris).map(uri::resolve).forEach(System.out::println);
            System.out.println();
        }
    }
}

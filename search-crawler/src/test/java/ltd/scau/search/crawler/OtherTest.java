package ltd.scau.search.crawler;

import ltd.scau.search.commons.util.URIs;
import org.junit.Test;

import java.net.URI;
import java.util.Arrays;

/**
 * @author Weijie Wu
 */
public class OtherTest {

    @Test
    public void uri() {
        String[] uris = {
                "mailto:zhaopin@pci-suntektech.com",
                "https://scau.ltd",
                "https://scau.ltd/",
                "/teacherinfo/teacher-100.html",
                "teacherinfo/teacher-100.html",
        };
        Arrays.stream(uris).map(URI::create)
                .forEach(
                        u -> System.out.println(String.format("%80s >> %s %s %s %s", u, u.getScheme(), u.getHost(), u.getPort(), u.getPath()))
                );
    }

    private static final String[] strings = {
            "http://wcce.scau.edu.cn",
            "http://wcce.scau.edu.cn/",
            "http://wcce.scau.edu.cn/cncontent.asp?id=190",
            "http://info.scau.edu.cn/teacherinfo/teacher-18.html",
    };

    private static final String[] paths = {
            "cncontent.asp?id=1",
            "/cncontent.asp?id=10",
            "/",
            "?id=2333",
            "#/manage",
            "mailto:wuweijie.io@qq.com",
            "http://search.scau.ltd",
    };

    @Test
    public void resolve() {
        for (String u : strings) {
            URI uri = URI.create(u);
            Arrays.stream(paths).map(uri::resolve).forEach(System.out::println);
            System.out.println();
        }
    }

    @Test
    public void relativize() {
        for (String u : strings) {
            URI uri = URI.create(u);
            Arrays.stream(paths).map(URI::create).map(uri::relativize).forEach(System.out::println);
            System.out.println();
        }
    }

    @Test
    public void resolveImpl() {
        Arrays.stream(strings)
                .map(URI::create)
                .forEach(uri -> {
//                    Arrays.stream(paths).map(p -> CrawlerHelper.resolve(uri, p)).forEach(System.out::println);
                    Arrays.stream(paths).forEach(p ->
                            System.out.println(String.format("%80s %-30s >>> %-50s", uri, p, URIs.resolve(uri, p)))
                    );
                });
    }
}

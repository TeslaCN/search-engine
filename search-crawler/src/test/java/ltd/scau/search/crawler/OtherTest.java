package ltd.scau.search.crawler;

import org.junit.Test;

import java.net.URI;

/**
 * @author Weijie Wu
 */
public class OtherTest {

    @Test
    public void uri() {
        URI uri = URI.create("mailto:zhaopin@pci-suntektech.com");
        System.out.println(uri);
    }
}

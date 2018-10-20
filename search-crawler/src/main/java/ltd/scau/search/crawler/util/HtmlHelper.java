package ltd.scau.search.crawler.util;

import org.apache.http.entity.ContentType;
import org.jsoup.Jsoup;

import java.nio.charset.Charset;
import java.util.Optional;

/**
 * @author Weijie Wu
 */
public class HtmlHelper {

    public static Charset getCharsetInMeta(String html) {
        Charset contentTypeCharset;
        Optional<String> optional = Jsoup.parse(html).getElementsByTag("meta")
                .stream()
                .filter(e -> e.attr("http-equiv").equalsIgnoreCase("Content-Type"))
                .map(e -> e.attr("content")).findFirst();
        if (!optional.isPresent()) {
            return null;
        }
        String contentTypeInMeta = optional.get();
        ContentType type = ContentType.parse(contentTypeInMeta);
        contentTypeCharset = type.getCharset();
        return contentTypeCharset;
    }
}

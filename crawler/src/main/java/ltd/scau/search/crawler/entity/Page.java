package ltd.scau.search.crawler.entity;

import java.net.URI;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Weijie Wu
 */
public class Page {

    private URI uri;

    private String html;

    private Set<URI> hrefs = new HashSet<>();

    private int code;

    private Page(PageBuilder b) {
        this.uri = b.uri;
        this.html = b.html;
        this.hrefs.addAll(b.hrefs);
        this.code = b.code;
    }

    public static PageBuilder newPage(String uri) {
        return newPage(URI.create(uri));
    }

    public static PageBuilder newPage(URI uri) {
        return new PageBuilder(uri);
    }

    public static final class PageBuilder {
        private URI uri;
        private String html;
        private Set<URI> hrefs = new HashSet<>();
        private int code;

        private PageBuilder(String uri) {
            this(URI.create(uri));
        }

        private PageBuilder(URI uri) {
            this.uri = uri;
        }

        public PageBuilder html(String html) {
            this.html = html;
            return this;
        }

        public PageBuilder hrefs(Set<String> hrefs) {
            this.hrefs.addAll(hrefs.stream().map(uri::resolve).collect(Collectors.toSet()));
            return this;
        }

        public PageBuilder hrefs(String... hrefs) {
            this.hrefs.addAll(Arrays.stream(hrefs).map(uri::resolve).collect(Collectors.toSet()));
            return this;
        }

        public PageBuilder code(int code) {
            this.code = code;
            return this;
        }

        public Page build() {
            return new Page(this);
        }
    }

    @Override
    public String toString() {
        return "Page{" +
                "uri=" + uri +
                ", html='" + html + '\'' +
                ", hrefs=" + hrefs +
                ", code=" + code +
                '}';
    }
}

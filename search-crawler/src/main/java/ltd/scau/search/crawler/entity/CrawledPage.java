package ltd.scau.search.crawler.entity;

import java.io.Serializable;
import java.net.URI;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Weijie Wu
 */
public class CrawledPage implements Serializable {

    private static final long serialVersionUID = 413703326668658581L;

    private final URI uri;

    private final String title;

    private final String html;

    private final String content;

    private final Set<URI> hrefs = new HashSet<>();

    private final int code;

    private final Date time;

    private CrawledPage(PageBuilder b) {
        this.uri = b.uri;
        this.title = b.title;
        this.html = b.html;
        this.hrefs.addAll(b.hrefs);
        this.code = b.code;
        this.time = b.time;
        this.content = b.content;
    }

    public static PageBuilder newPage(String uri) {
        return newPage(URI.create(uri));
    }

    public static PageBuilder newPage(URI uri) {
        return new PageBuilder(uri);
    }

    public static final class PageBuilder {
        private URI uri;
        private String title;
        private String html;
        private String content;
        private Set<URI> hrefs = new HashSet<>();
        private int code;
        private Date time;

        private PageBuilder(String uri) {
            this(URI.create(uri));
        }

        private PageBuilder(URI uri) {
            this.uri = uri;
        }

        public PageBuilder title(String title) {
            this.title = title;
            return this;
        }

        public PageBuilder html(String html) {
            this.html = html;
            return this;
        }

        public PageBuilder content(String content) {
            this.content = content;
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

        public PageBuilder when(Date time) {
            this.time = time;
            return this;
        }

        public CrawledPage build() {
            return new CrawledPage(this);
        }
    }

    @Override
    public String toString() {
        return "CrawledPage{" +
                "uri=" + uri +
                ", title='" + title + '\'' +
                ", hrefs=" + hrefs +
                ", code=" + code +
                ", time=" + time +
                '}';
    }

    public String getContent() {
        return content;
    }

    public Date getTime() {
        return time;
    }

    public String getTitle() {
        return title;
    }

    public URI getUri() {
        return uri;
    }

    public String getHtml() {
        return html;
    }

    public Set<URI> getHrefs() {
        return hrefs;
    }

    public int getCode() {
        return code;
    }
}

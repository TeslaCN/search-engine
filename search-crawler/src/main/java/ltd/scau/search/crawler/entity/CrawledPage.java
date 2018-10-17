package ltd.scau.search.crawler.entity;

import java.io.Serializable;
import java.net.URI;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Weijie Wu
 */
public class CrawledPage implements Serializable {

    private static final long serialVersionUID = 413703326668658581L;

    private final URI uri;

    private final List<String> title;

    private final String html;

    private final List<String> content;

    private final Set<URI> hrefs;

    private final Set<String> tags;

    private final int code;

    private final Date time;

    private CrawledPage(PageBuilder b) {
        this.uri = b.uri;
        this.title = b.title;
        this.html = b.html;
        this.hrefs = b.hrefs;
        this.tags = b.tags;
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
        private List<String> title;
        private String html;
        private List<String> content;
        private Set<URI> hrefs = new HashSet<>();
        private Set<String> tags = new HashSet<>();
        private int code;
        private Date time;

        private PageBuilder(String uri) {
            this(URI.create(uri));
        }

        private PageBuilder(URI uri) {
            this.uri = uri;
        }

        public PageBuilder title(List<String> title) {
            this.title = title;
            return this;
        }

        public PageBuilder html(String html) {
            this.html = html;
            return this;
        }

        public PageBuilder content(List<String> content) {
            this.content = content;
            return this;
        }

        public PageBuilder tags(Set<String> tags) {
            this.tags.addAll(tags);
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
                ", title=" + title +
                ", hrefs=" + hrefs +
                ", tags=" + tags +
                ", code=" + code +
                ", time=" + time +
                '}';
    }

    public List<String> getContent() {
        return content;
    }

    public Date getTime() {
        return time;
    }

    public List<String> getTitle() {
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

    public Set<String> getTags() {
        return tags;
    }

    public int getCode() {
        return code;
    }
}

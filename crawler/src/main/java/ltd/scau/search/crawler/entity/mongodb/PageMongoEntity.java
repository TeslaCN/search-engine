package ltd.scau.search.crawler.entity.mongodb;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.List;

/**
 * @author Weijie Wu
 */
@Document(collection = "page")
public class PageMongoEntity implements Serializable {

    private static final long serialVersionUID = -5688047194163958507L;

    @Indexed(unique = true)
    private String uri;

    private String html;

    @Indexed
    private List<String> tags;

    private Long timestamp;

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getHtml() {
        return html;
    }

    public void setHtml(String html) {
        this.html = html;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    @Override
    public String toString() {
        return "Page{" +
                "uri='" + uri + '\'' +
                ", html='" + html + '\'' +
                ", tags=" + tags +
                '}';
    }
}

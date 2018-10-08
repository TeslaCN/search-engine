package ltd.scau.search.commons.entity.mongodb;

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

    @Indexed
    private String title;

    private String html;

    @Indexed
    private List<String> tags;

    private Long timestamp;

    private Integer code;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

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

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "PageMongoEntity{" +
                "uri='" + uri + '\'' +
                ", title='" + title + '\'' +
                ", tags=" + tags +
                ", timestamp=" + timestamp +
                ", code=" + code +
                '}';
    }
}

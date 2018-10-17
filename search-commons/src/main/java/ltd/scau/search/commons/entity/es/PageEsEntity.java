package ltd.scau.search.commons.entity.es;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.util.List;

/**
 * @author Weijie Wu
 */
@Document(createIndex = false, indexName = "ds-search", type = "page")
public class PageEsEntity {

    @Id
    private String id;

    private String uri;

    private List<String> tags;

    private List<String> title;

    private List<String> content;

    private Long crawlDate;

    private Integer code;

    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return "PageEsEntity{" +
                "id='" + id + '\'' +
                ", uri='" + uri + '\'' +
                ", tags=" + tags +
                ", title='" + title + '\'' +
                ", crawlDate=" + crawlDate +
                ", code=" + code +
                '}';
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
        this.id = DigestUtils.md5Hex(uri);
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public List<String> getTitle() {
        return title;
    }

    public void setTitle(List<String> title) {
        this.title = title;
    }

    public List<String> getContent() {
        return content;
    }

    public void setContent(List<String> content) {
        this.content = content;
    }

    public Long getCrawlDate() {
        return crawlDate;
    }

    public void setCrawlDate(Long crawlDate) {
        this.crawlDate = crawlDate;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}

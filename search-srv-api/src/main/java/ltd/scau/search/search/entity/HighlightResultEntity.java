package ltd.scau.search.search.entity;

import java.util.Date;
import java.util.List;

/**
 * @author Weijie Wu
 */
public class HighlightResultEntity {

    private String uri;

    private String title;

    private List<String> highlights;

    private Date crawlDate;

    private List<String> tags;

    @Override
    public String toString() {
        return "SearchResultEntity{" +
                "uri='" + uri + '\'' +
                ", title='" + title + '\'' +
                ", highlights=" + highlights +
                ", crawlDate=" + crawlDate +
                ", tags=" + tags +
                '}';
    }

    public Date getCrawlDate() {
        return crawlDate;
    }

    public void setCrawlDate(Date crawlDate) {
        this.crawlDate = crawlDate;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getHighlights() {
        return highlights;
    }

    public void setHighlights(List<String> highlights) {
        this.highlights = highlights;
    }
}

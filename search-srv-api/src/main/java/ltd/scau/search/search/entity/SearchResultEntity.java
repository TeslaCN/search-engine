package ltd.scau.search.search.entity;

import java.util.List;

/**
 * @author Weijie Wu
 */
public class SearchResultEntity {

    private String uri;

    private String title;

    private List<String> highlights;

    @Override
    public String toString() {
        return "SearchResultEntity{" +
                "uri='" + uri + '\'' +
                ", title='" + title + '\'' +
                ", highlights=" + highlights +
                '}';
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

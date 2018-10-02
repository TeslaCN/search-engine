package ltd.scau.search.crawler.entity;

import java.io.Serializable;
import java.net.URI;
import java.util.Date;

/**
 * @author Weijie Wu
 */
public class Mission implements Serializable {

    private static final long serialVersionUID = -4088060927469120820L;

    private final URI uri;

    private final Date createDate = new Date();

    private Mission(URI uri) {
        this.uri = uri;
    }

    public static Mission create(URI uri) {
        return new Mission(uri);
    }

    @Override
    public String toString() {
        return "Mission{" +
                "uri=" + uri +
                ", createDate=" + createDate +
                '}';
    }

    public URI getUri() {
        return uri;
    }

    public Date getCreateDate() {
        return createDate;
    }
}

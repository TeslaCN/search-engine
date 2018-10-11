package ltd.scau.search.commons.entity;

import java.io.Serializable;
import java.net.URI;
import java.util.Date;

/**
 * @author Weijie Wu
 */
public class Mission implements Serializable {

    private static final long serialVersionUID = -4088060927469120820L;

    private URI uri;

    private Date createDate = new Date();

    public Mission() {

    }

    private Mission(URI uri) {
        this.uri = uri;
    }

    public static Mission create(URI uri) {
        return new Mission(uri);
    }

    public static Mission[] create(URI... uri) {
        Mission[] missions = new Mission[uri.length];
        for (int i = 0; i < missions.length; i++) {
            missions[i] = create(uri[i]);
        }
        return missions;
    }

    @Override
    public String toString() {
        return "Mission{" +
                "uri=" + uri +
                ", createDate=" + createDate +
                '}';
    }

    public void setUri(URI uri) {
        this.uri = uri;
    }

    public URI getUri() {
        return uri;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getCreateDate() {
        return createDate;
    }
}

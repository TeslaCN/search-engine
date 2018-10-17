package ltd.scau.search.commons.entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

/**
 * @author Weijie Wu
 */
@Entity(name = "t_page_structure")
public class PageStructure {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String host;

    private String pathPattern;

    private String titleXpath;

    private String contentXpath;

    private String hrefXpath;

    private String tagXpath;

    @Column(insertable = false)
    private Timestamp createTime;

    private Integer options;

    @Override
    public String toString() {
        return "PageStructure{" +
                "id=" + id +
                ", host='" + host + '\'' +
                ", pathPattern='" + pathPattern + '\'' +
                ", titleXpath='" + titleXpath + '\'' +
                ", contentXpath='" + contentXpath + '\'' +
                ", hrefXpath='" + hrefXpath + '\'' +
                ", tagXpath='" + tagXpath + '\'' +
                ", createTime=" + createTime +
                ", options=" + options +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PageStructure structure = (PageStructure) o;
        return Objects.equals(id, structure.id);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getPathPattern() {
        return pathPattern;
    }

    public void setPathPattern(String pathPattern) {
        this.pathPattern = pathPattern;
    }

    public String getTitleXpath() {
        return titleXpath;
    }

    public void setTitleXpath(String titleXpath) {
        this.titleXpath = titleXpath;
    }

    public String getContentXpath() {
        return contentXpath;
    }

    public void setContentXpath(String contentXpath) {
        this.contentXpath = contentXpath;
    }

    public String getHrefXpath() {
        return hrefXpath;
    }

    public void setHrefXpath(String hrefXpath) {
        this.hrefXpath = hrefXpath;
    }

    public String getTagXpath() {
        return tagXpath;
    }

    public void setTagXpath(String tagXpath) {
        this.tagXpath = tagXpath;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public Integer getOptions() {
        return options;
    }

    public void setOptions(Integer options) {
        this.options = options;
    }
}

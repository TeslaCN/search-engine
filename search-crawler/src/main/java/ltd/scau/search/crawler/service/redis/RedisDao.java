package ltd.scau.search.crawler.service.redis;

/**
 * @author Weijie Wu
 */
public interface RedisDao {

    String get(String key);

    void set(String key, String value);

    void setAdd(String key, String... values);

    boolean setContains(String key, String value);

}

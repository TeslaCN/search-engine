package ltd.scau.search.crawler.service.mongodb;

import ltd.scau.search.crawler.entity.mongodb.PageMongoEntity;

/**
 * @author Weijie Wu
 */
public interface PageDao {

    PageMongoEntity findByUri(String uri);

    void save(PageMongoEntity page);

    void update(PageMongoEntity page);

    void delete(PageMongoEntity page);
}

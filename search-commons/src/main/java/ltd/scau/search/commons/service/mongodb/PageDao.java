package ltd.scau.search.commons.service.mongodb;


import ltd.scau.search.commons.entity.mongodb.PageMongoEntity;

/**
 * @author Weijie Wu
 */
@Deprecated
public interface PageDao {

    PageMongoEntity findByUri(String uri);

    void save(PageMongoEntity page);

    void update(PageMongoEntity page);

    void delete(PageMongoEntity page);
}

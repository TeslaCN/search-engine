package ltd.scau.search.commons.service.mongodb.impl;

import com.mongodb.client.model.Updates;
import ltd.scau.search.commons.entity.mongodb.PageMongoEntity;
import ltd.scau.search.commons.service.mongodb.PageDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

/**
 * @author Weijie Wu
 */
@Service
@Deprecated
public class PageDaoImpl implements PageDao {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Value("${default.charset}")
    private String defaultCharset;

    @Override
    public PageMongoEntity findByUri(String uri) {
        PageMongoEntity page = mongoTemplate.findOne(Query.query(Criteria.where("uri").is(uri)), PageMongoEntity.class);
        return page;
    }

    @Override
    public void save(PageMongoEntity page) {
        mongoTemplate.save(page);
    }

    @Override
    public void update(PageMongoEntity page) {
    }

    @Override
    public void delete(PageMongoEntity page) {
        mongoTemplate.remove(Query.query(Criteria.where("uri").is(page.getUri())), PageMongoEntity.class);
    }
}

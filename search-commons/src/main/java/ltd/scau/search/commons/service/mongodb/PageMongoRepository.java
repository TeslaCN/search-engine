package ltd.scau.search.commons.service.mongodb;

import ltd.scau.search.commons.entity.mongodb.PageMongoEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author Weijie Wu
 */
public interface PageMongoRepository extends MongoRepository<PageMongoEntity, String> {

    Page<PageMongoEntity> findByUri(String uri, Pageable pageable);
}

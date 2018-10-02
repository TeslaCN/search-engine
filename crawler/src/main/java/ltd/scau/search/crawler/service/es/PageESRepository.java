package ltd.scau.search.crawler.service.es;

import ltd.scau.search.crawler.entity.es.PageEsEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * @author Weijie Wu
 */
public interface PageESRepository extends ElasticsearchRepository<PageEsEntity, String> {

    Page<PageEsEntity> findByContentLike(String content, Pageable pageable);
}

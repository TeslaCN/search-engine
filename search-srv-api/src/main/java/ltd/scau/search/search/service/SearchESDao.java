package ltd.scau.search.search.service;

import ltd.scau.search.commons.entity.es.PageEsEntity;
import ltd.scau.search.search.entity.SearchResultEntity;

import java.util.List;

/**
 * @author Weijie Wu
 */
public interface SearchESDao {

    List<SearchResultEntity> findByKeyHighlight(String key);

}

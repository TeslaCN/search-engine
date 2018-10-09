package ltd.scau.search.search.service;

import ltd.scau.search.search.entity.SearchResultEntity;

import java.util.List;

/**
 * @author Weijie Wu
 */
public interface SearchESDao {

    List<SearchResultEntity> findByKeyHighlight(String key);

    List<SearchResultEntity> findLikeKeyHighlight(String key);

}

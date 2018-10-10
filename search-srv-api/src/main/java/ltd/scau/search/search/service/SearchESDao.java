package ltd.scau.search.search.service;

import ltd.scau.search.search.entity.SearchResultEntity;

import java.util.List;

/**
 * @author Weijie Wu
 */
public interface SearchESDao {

    List<SearchResultEntity> findByKeyHighlight(String key, Integer page, Integer size);

    List<SearchResultEntity> findLikeKeyHighlight(String key, Integer page, Integer size);

    List<String> suggestions(String key);
}

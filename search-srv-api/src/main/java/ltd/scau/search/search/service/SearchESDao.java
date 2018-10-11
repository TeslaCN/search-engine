package ltd.scau.search.search.service;

import ltd.scau.search.search.entity.Hits;

import java.util.List;

/**
 * @author Weijie Wu
 */
public interface SearchESDao {

    Hits findByKeyHighlight(String key, Integer page, Integer size);

    Hits findLikeKeyHighlight(String key, Integer page, Integer size);

    List<String> suggestions(String key);
}

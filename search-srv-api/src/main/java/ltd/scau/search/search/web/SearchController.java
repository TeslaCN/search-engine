package ltd.scau.search.search.web;

import ltd.scau.search.commons.entity.ResponseData;
import ltd.scau.search.commons.entity.es.PageEsEntity;
import ltd.scau.search.commons.entity.SearchHistory;
import ltd.scau.search.search.service.SearchESDao;
import ltd.scau.search.commons.service.es.PageESRepository;
import ltd.scau.search.commons.service.SearchHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author Weijie Wu
 */
@RestController
public class SearchController {

    @Autowired
    private PageESRepository pageESRepository;

    @Autowired
    private SearchESDao pageESDao;

    @Autowired
    private SearchHistoryRepository searchHistoryRepository;

    @GetMapping("/query")
    public Page<PageEsEntity> search(@RequestParam(required = false) String key, @RequestParam(required = false) Integer page, @RequestParam(required = false) Integer size) {
        key = key == null ? "" : key;
        page = page == null || page < 0 ? 0 : page;
        size = size == null || size < 0 ? 10 : size;
        return pageESRepository.findByContentLike(key, PageRequest.of(page, size));
    }

    @GetMapping("/search")
    public ResponseData highlight(@RequestParam String key, Integer page, Integer size) {
        page = page == null || page < 0 ? 0 : page;
        size = size == null || size < 0 ? 10 : size;
        SearchHistory history = new SearchHistory();
        history.setSearchKey(key);
        history.setUserId(0L);
        searchHistoryRepository.saveAndFlush(history);
        return ResponseData.aData().data(pageESDao.findByKeyHighlight(key, page, size)).build();
    }

    @GetMapping("/like")
    public ResponseData like(@RequestParam String key, Integer page, Integer size) {
        page = page == null || page < 0 ? 0 : page;
        size = size == null || size < 0 ? 10 : size;
        SearchHistory history = new SearchHistory();
        history.setSearchKey(key);
        history.setUserId(0L);
        searchHistoryRepository.saveAndFlush(history);
        return ResponseData.aData().data(pageESDao.findLikeKeyHighlight(key, page, size)).build();
    }

    @GetMapping("/prefix")
    public List<Map<String, String>> prefix(@RequestParam String key) {
        List<String> suggestions = pageESDao.suggestions(key);
        List<Map<String, String>> results = new LinkedList<>();
        suggestions.forEach(s -> {
            Map<String, String> map = new HashMap<>();
            map.put("value", s);
            results.add(map);
        });
        return results;
    }
}

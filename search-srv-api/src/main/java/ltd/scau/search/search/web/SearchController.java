package ltd.scau.search.search.web;

import ltd.scau.search.commons.entity.es.PageEsEntity;
import ltd.scau.search.search.entity.SearchResultEntity;
import ltd.scau.search.search.service.SearchESDao;
import ltd.scau.search.commons.service.es.PageESRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Weijie Wu
 */
@RestController
public class SearchController {

    @Autowired
    private PageESRepository pageESRepository;

    @GetMapping("/query")
    public Page<PageEsEntity> search(@RequestParam(required = false) String key, @RequestParam(required = false) Integer page, @RequestParam(required = false) Integer size) {
        key = key == null ? "" : key;
        page = page == null || page < 0 ? 0 : page;
        size = size == null || size < 0 ? 10 : size;
        Page<PageEsEntity> entities = pageESRepository.findByContentLike(key, PageRequest.of(page, size));
        return entities;
    }

    @Autowired
    private SearchESDao pageESDao;

    @GetMapping("/search")
    public List<SearchResultEntity> highlight(@RequestParam(required = false) String key) {
        return pageESDao.findByKeyHighlight(key);
    }

    @GetMapping("/like")
    public List<SearchResultEntity> like(@RequestParam(required = false) String key) {
        return pageESDao.findLikeKeyHighlight(key);
    }
}

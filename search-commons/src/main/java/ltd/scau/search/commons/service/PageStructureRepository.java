package ltd.scau.search.commons.service;

import ltd.scau.search.commons.entity.PageStructure;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.net.URI;
import java.util.List;

/**
 * @author Weijie Wu
 */
public interface PageStructureRepository extends JpaRepository<PageStructure, Long> {

    @Query(value = "select * from t_page_structure where host = ?1 and ?2 regexp path_pattern order by length(path_pattern) desc", nativeQuery = true)
    List<PageStructure> findAllByHostAndPathRegexPathPattern(String host, String path);

    default List<PageStructure> findAllByHostAndPathRegexPathPattern(URI uri) {
        return findAllByHostAndPathRegexPathPattern(uri.getHost(), uri.getPath());
    }

    @Query(value = "select * from t_page_structure where host = ?1 and ?2 regexp path_pattern order by length(path_pattern) desc limit 1", nativeQuery = true)
    PageStructure findLongestMatchByHostAndPathRegexPathPattern(String host, String path);

    default PageStructure findLongestMatchByHostAndPathRegexPathPattern(URI uri) {
        return findLongestMatchByHostAndPathRegexPathPattern(uri.getHost(), uri.getPath());
    }

    @Query(value = "select * from t_page_structure where ?1 regexp host and ?2 regexp path_pattern order by length(host), length(path_pattern) desc limit 1", nativeQuery = true)
    List<PageStructure> findAllRegexMatchHostAndPathPattern(String host, String path);

    default List<PageStructure> findAllRegexMatchHostAndPathPattern(URI uri) {
        return findAllRegexMatchHostAndPathPattern(uri.getHost(), uri.getPath());
    }

    @Query(value = "select * from t_page_structure where ?1 regexp host and ?2 regexp path_pattern order by length(host), length(path_pattern) desc limit 1", nativeQuery = true)
    PageStructure findRegexMatchHostAndPathPattern(String host, String path);

    default PageStructure findRegexMatchHostAndPathPattern(URI uri) {
        return findRegexMatchHostAndPathPattern(uri.getHost(), uri.getPath());
    }
}

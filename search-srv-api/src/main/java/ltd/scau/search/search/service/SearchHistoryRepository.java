package ltd.scau.search.search.service;

import ltd.scau.search.search.entity.SearchHistory;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Weijie Wu
 */
public interface SearchHistoryRepository extends JpaRepository<SearchHistory, Long> {

}

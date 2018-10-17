package ltd.scau.search.commons.service;

import ltd.scau.search.commons.entity.SearchHistory;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Weijie Wu
 */
public interface SearchHistoryRepository extends JpaRepository<SearchHistory, Long> {

}

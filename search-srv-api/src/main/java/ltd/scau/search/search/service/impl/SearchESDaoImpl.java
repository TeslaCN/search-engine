package ltd.scau.search.search.service.impl;

import ltd.scau.search.search.entity.SearchResultEntity;
import ltd.scau.search.search.service.SearchESDao;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Weijie Wu
 */
@Service
public class SearchESDaoImpl implements SearchESDao {

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    @Override
    public List<SearchResultEntity> findByKeyHighlight(String key) {
        Client client = elasticsearchTemplate.getClient();

        HighlightBuilder highlightBuilder = new HighlightBuilder();
        highlightBuilder.field("content");

        SearchResponse searchResponse = client
                .prepareSearch("ds-search")
                .setQuery(QueryBuilders.matchPhraseQuery("content", key))
                .highlighter(highlightBuilder)
                .execute().actionGet();
        SearchHits hits = searchResponse.getHits();

        List<SearchResultEntity> entities = new ArrayList<>();
        hits.forEach(hit -> {
            Map<String, Object> sourceMap = hit.getSourceAsMap();

            Map<String, HighlightField> highlightFields = hit.getHighlightFields();
            HighlightField field = highlightFields.get("content");
            Text[] texts = field.getFragments();

            SearchResultEntity entity = new SearchResultEntity();
            entity.setUri(sourceMap.get("uri").toString());
            entity.setHighlights(Arrays.stream(texts).map(Text::toString).collect(Collectors.toList()));
            entities.add(entity);
        });

        return entities;
    }
}

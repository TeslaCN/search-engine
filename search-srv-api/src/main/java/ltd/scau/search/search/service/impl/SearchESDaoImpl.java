package ltd.scau.search.search.service.impl;

import ltd.scau.search.search.entity.Hits;
import ltd.scau.search.search.entity.HighlightResultEntity;
import ltd.scau.search.search.service.SearchESDao;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.PrefixQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
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
    public Hits findByKeyHighlight(String key, Integer page, Integer size) {
        return highlightQuery(QueryBuilders.boolQuery()
                .should(QueryBuilders.matchPhraseQuery("title", key))
                .should(QueryBuilders.matchPhraseQuery("content", key)), page, size);
    }

    private Hits highlightQuery(BoolQueryBuilder queryBuilder, Integer page, Integer size) {
        Client client = elasticsearchTemplate.getClient();

        HighlightBuilder highlightBuilder = new HighlightBuilder();
        highlightBuilder.field("title").field("content");

        SearchResponse searchResponse = client
                .prepareSearch("ds-search")
                .setQuery(queryBuilder)
                .highlighter(highlightBuilder)
                .setFrom(page * size)
                .setSize(size)
                .execute().actionGet();

        SearchHits hits = searchResponse.getHits();

        long totalHits = hits.totalHits;

        List<HighlightResultEntity> entities = new ArrayList<>();
        hits.forEach(hit -> {
            Map<String, Object> sourceMap = hit.getSourceAsMap();

            Map<String, HighlightField> highlightFields = hit.getHighlightFields();

            HighlightField contentHighlight = highlightFields.get("content");
            Text[] contentTexts = contentHighlight.getFragments();

            HighlightResultEntity entity = new HighlightResultEntity();

            HighlightField titleHighlight = highlightFields.get("title");
            if (titleHighlight != null) {
                Text[] titleTexts = titleHighlight.getFragments();
                entity.setTitle(String.join(" ", Arrays.stream(titleTexts).map(Text::toString).toArray(String[]::new)));
            } else {
                entity.setTitle(sourceMap.get("title").toString());
            }

            entity.setUri(sourceMap.get("uri").toString());
            entity.setHighlights(Arrays.stream(contentTexts).map(Text::toString).collect(Collectors.toList()));
            entities.add(entity);
        });

        return Hits.aHits().entities(entities).totalHits(totalHits).build();
    }

    @Override
    public Hits findLikeKeyHighlight(String key, Integer page, Integer size) {
        return highlightQuery(QueryBuilders.boolQuery()
                .should(QueryBuilders.matchQuery("title", key))
                .should(QueryBuilders.matchQuery("content", key)), page, size);
    }

    @Override
    public List<String> suggestions(String key) {
        PrefixQueryBuilder query = QueryBuilders.prefixQuery("title", key);
        Client client = elasticsearchTemplate.getClient();

        SearchResponse response = client.prepareSearch("ds-search").setQuery(query).execute().actionGet();
        SearchHit[] hits = response.getHits().getHits();
        return Arrays.stream(hits).map(hit -> hit.getSourceAsMap().get("title").toString()).collect(Collectors.toList());
    }
}

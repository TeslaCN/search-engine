package ltd.scau.search.search.entity;

import java.util.List;

/**
 * @author Weijie Wu
 */
public class Hits {

    private long totalHits;

    private List<HighlightResultEntity> entities;

    public Hits() {
    }

    public long getTotalHits() {
        return totalHits;
    }

    public List<HighlightResultEntity> getEntities() {
        return entities;
    }

    public static HitsBuilder aHits() {
        return new HitsBuilder();
    }

    public static final class HitsBuilder {
        private long totalHits;
        private List<HighlightResultEntity> entities;

        private HitsBuilder() {
        }

        public HitsBuilder totalHits(long totalHits) {
            this.totalHits = totalHits;
            return this;
        }

        public HitsBuilder entities(List<HighlightResultEntity> entities) {
            this.entities = entities;
            return this;
        }

        public Hits build() {
            Hits hits = new Hits();
            hits.entities = this.entities;
            hits.totalHits = this.totalHits;
            return hits;
        }
    }
}

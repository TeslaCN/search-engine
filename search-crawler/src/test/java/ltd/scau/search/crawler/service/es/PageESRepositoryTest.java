package ltd.scau.search.crawler.service.es;

import ltd.scau.search.commons.entity.es.PageEsEntity;
import ltd.scau.search.commons.service.es.PageESRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;

import static org.junit.Assert.*;

/**
 * @author Weijie Wu
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class PageESRepositoryTest {

    @Autowired
    private PageESRepository pageESRepository;

    @Test
    public void insert() {
        PageEsEntity entity = new PageEsEntity();

        entity.setUri("http://info.scau.edu.cn");
        entity.setTags(Arrays.asList("gitlab", "scau"));
        entity.setContent(Arrays.asList(""));
        entity.setCrawlDate(System.currentTimeMillis());
        pageESRepository.index(entity);
    }
}
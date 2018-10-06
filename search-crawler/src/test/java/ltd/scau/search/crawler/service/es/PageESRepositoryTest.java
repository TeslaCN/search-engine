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
        entity.setContent("9月18日晚，特邀嘉宾臧根林博士在丁颖礼堂为数学与信息学院、软件学院795名本科新生及59名研究生新生上大学职业规划第一课——生涯规划从入学开始。");
        entity.setCrawlDate(System.currentTimeMillis());
        pageESRepository.index(entity);
    }
}
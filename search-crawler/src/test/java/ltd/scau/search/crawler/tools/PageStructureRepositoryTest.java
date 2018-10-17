package ltd.scau.search.crawler.tools;

import ltd.scau.search.commons.entity.PageStructure;
import ltd.scau.search.commons.service.PageStructureRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.net.URI;

import static org.junit.Assert.*;

/**
 * @author Weijie Wu
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class PageStructureRepositoryTest {

    @Autowired
    private PageStructureRepository pageStructureRepository;

    @Test
    public void testInsert() {
        URI u = URI.create("http://info.scau.edu.cn/news-3307.html#");
        PageStructure s = new PageStructure();
        s.setHost(u.getHost());
        s.setPathPattern("/news.*");
        s.setTitleXpath("/html/body/div[2]/div[1]/h2");
        s.setContentXpath("/html/body/div[2]");
        pageStructureRepository.saveAndFlush(s);
    }

}
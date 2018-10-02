package ltd.scau.search.crawler.service.mongodb.impl;

import ltd.scau.search.crawler.entity.mongodb.PageMongoEntity;
import ltd.scau.search.crawler.service.mongodb.PageDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;

/**
 * @author Weijie Wu
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class PageDaoImplTest {

    @Autowired
    private PageDao pageDao;

    @Test
    public void insert() {
        PageMongoEntity p = new PageMongoEntity();
        p.setUri("https://scau.ltd/");
        p.setHtml("<body></body>");
        p.setTags(Arrays.asList("scau", "gitlab"));
        pageDao.insert(p);
    }

    @Test
    public void get() {
        PageMongoEntity page = pageDao.findByUri("https://scau.ltd");
        System.out.println(page);
    }
}
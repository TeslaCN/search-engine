package ltd.scau.search.crawler.service.redis.impl;

import ltd.scau.search.crawler.service.redis.RedisDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

/**
 * @author Weijie Wu
 */
@Service
public class RedisDaoImpl implements RedisDao {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public void set(String key, String value) {
        stringRedisTemplate.opsForValue().set(key, value);
    }

    @Override
    public String get(String key) {
        return stringRedisTemplate.opsForValue().get(key);
    }

    @Override
    public void setAdd(String key, String... values) {
        stringRedisTemplate.opsForSet().add(key, values);
    }

    @Override
    public boolean setContains(String key, String value) {
        return stringRedisTemplate.opsForSet().isMember(key, value);
    }
}

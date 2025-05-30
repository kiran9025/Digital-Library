package org.digitalLibrary.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class RedisService {

    private final RedisTemplate<String, Object> redisTemplate;

    @Autowired
    public RedisService(RedisTemplate<String, Object> redisTemplate ){
        this.redisTemplate = redisTemplate;
    }

    public void save(String key, Object value, long timeout, TimeUnit unit){
        this.redisTemplate.opsForValue().set(key, value, timeout,unit);
    }

    public Object get(String key){
        return this.redisTemplate.opsForValue().get(key);
    }

    public void delete(String key){
        this.redisTemplate.delete(key);
    }
}

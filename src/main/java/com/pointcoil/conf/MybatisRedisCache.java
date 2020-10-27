package com.pointcoil.conf;

import com.pointcoil.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.cache.Cache;
import org.mybatis.logging.Logger;
import org.mybatis.logging.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Created by WuShuang on 2019/11/12.
 */
@Slf4j
public class MybatisRedisCache implements Cache {



    @Autowired
    private RedisUtil redisUtil;

    private static RedisTemplate redisTemplate;

    private String id;

    private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock(true);


      public MybatisRedisCache(final String id) {
        if (id == null) {
            throw new IllegalArgumentException("Cache instances require an ID");
        }
        log.info("Redis Cache id " + id);
        this.id = id;
    }




    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public void putObject(Object key, Object value) {
        if (value != null && key != null) {
            redisTemplate.opsForValue().set(key.toString(), value);
        }
    }

    @Override
    public Object getObject(Object key) {
        try {
            if (key != null) {
                Object obj = redisTemplate.opsForValue().get(key.toString());
                return obj;
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return null;
    }

    @Override
    public Object removeObject(Object key) {
        try {
            if (key != null) {
                redisTemplate.delete(key.toString());
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return null;
    }

    @Override
    public void clear() {
        log.info("清空缓存");
        try {
            Set<String> keys = redisTemplate.keys("*:" + this.id + "*");
            if (!CollectionUtils.isEmpty(keys)) {
                redisTemplate.delete(keys);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    @Override
    public int getSize() {
        Long size = (Long) redisTemplate.execute(new RedisCallback<Long>() {
            @Override
            public Long doInRedis(RedisConnection connection) throws DataAccessException {
                return connection.dbSize();
            }
        });
        return size.intValue();
    }

    @Override
    public ReadWriteLock getReadWriteLock() {
        return this.readWriteLock;
    }


    public static void setRedisConnectionFactory(RedisTemplate redisTemplate) {
        MybatisRedisCache.redisTemplate = redisTemplate;
    }

}

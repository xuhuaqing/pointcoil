package com.pointcoil.conf;

import org.apache.ibatis.cache.Cache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/**
 * Created by WuShuang on 2019/11/12.
 */
@Component
public class RedisCacheTransfer {


    @Autowired
    public void setJedisConnectionFactory(RedisTemplate redisTemplate) {
        MybatisRedisCache.setRedisConnectionFactory(redisTemplate);
    }
}

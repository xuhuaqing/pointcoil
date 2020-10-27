package com.pointcoil.util;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.geo.Point;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName RedisUtil
 * @Author WuShuang
 * @Date 2019/4/16 0016 17:29
 * @Version 1.0
 **/
@Data
@Component
public class RedisUtil {


    /**
     * 缓存时间
     */
    private int expireTime = 30 * 60 * 60;

    @Autowired
    private RedisTemplate redisTemplate;

    @Resource(name="redisTemplate")
    private ValueOperations<String,Object> ops;

    public void geoadd(String businessId, Point point, Integer i ){
        redisTemplate.opsForGeo().add(businessId,point,i);
    }

    public List<Point> geopos(String businessId,Integer ... i ){
      return redisTemplate.opsForGeo().position(businessId,i);
    }

    public void removeRange(String key,String str){
        redisTemplate.opsForZSet().remove(key,str);
    }

    public Boolean isHash(String key,String find){
     return    redisTemplate.opsForHash().hasKey(key, find);
    }

    /**
     * 返回集合 从小到大
     * @param key
     * @return
     */
    public Set<String> zSetGetOne(String key){
      return redisTemplate.opsForZSet().range(key,0,0);
    }

    public Double zSetScore(String key,String find){
       return redisTemplate.opsForZSet().score(key,find);
    }

    public void hSet(String key, String field, String value){
        redisTemplate.opsForHash().put(key,field,value);
    }

    public String hGet(String key, String field){
      return (String) redisTemplate.opsForHash().get(key,field);
    }

    public List<?> lRangeLen(String key,int min,int max){
        return redisTemplate.opsForList().range(key,min,max);
    }

    public List<String> lRange(String key){
        return redisTemplate.opsForList().range(key, 0, -1);
    }

    public Map<String,String> entries(String key){
        return redisTemplate.opsForHash().entries(key);
    }

    public void deleteHash(String key, String field){
        redisTemplate.opsForHash().delete(key,field);
    }

    public Set<String> hkeys(String key){
        return redisTemplate.opsForHash().keys(key);
    }
    public Set<String> keys(String key){
        return redisTemplate.keys(key);
    }

    public void lPush(String key,Object value){
        redisTemplate.opsForList().leftPush(key,value);
    }


    public void lPushObj(String key,Object value){
        redisTemplate.opsForList().leftPush(key, value);
    }


    public void deleteList(String key,long count,Object value){
        redisTemplate.opsForList().remove(key,count,value);
    }


    public List lRangeObj(String key,int min,int max){
        return redisTemplate.opsForList().range(key,min,max);
    }

    public Long listSize(String key){
        return redisTemplate.opsForList().size(key);
    }

    public int countKey(String key){

        ops.increment(key, 1);

        return Integer.valueOf(redisTemplate.boundValueOps(key).get(0, -1));
    }


    /**
     * zset存入缓存
     */
    public void zSetPush(String key,String searchName,double value){
        redisTemplate.opsForZSet().add(key,searchName,value);
    }

    /**
     * 取出zset缓存中的值 (从大到小)
     */
    public Set<String> zSetRange(String key){
        return redisTemplate.opsForZSet().reverseRange(key, 0, 15);
    }

    /**
     * 批量删除对应的value
     *
     * @param keys
     */
    public void remove(final String... keys) {
        for (String key : keys) {
            remove(key);
        }
    }

    public Long execute(){
        Long execute = (Long) redisTemplate.execute(new RedisCallback<Long>() {
            @Override
            public Long doInRedis(RedisConnection connection) throws DataAccessException {
                return connection.dbSize();
            }
        });
        return execute;
    }

    /**
     * 批量删除key
     *
     * @param pattern
     */
    public void removePattern(final String pattern) {
        Set<Serializable> keys = redisTemplate.keys(pattern);
        if (keys.size() > 0) {
            redisTemplate.delete(keys);
        }
    }

    /**
     * 删除对应的value
     *
     * @param key
     */
    public void remove(final String key) {
        if (exists(key)) {
            redisTemplate.delete(key);
        }
    }

    /**
     * 判断缓存中是否有对应的value
     *
     * @param key
     * @return
     */
    public boolean exists(final String key) {
        return redisTemplate.hasKey(key);
    }

    /**
     * 读取缓存
     *
     * @param key
     * @return
     */
    public Object get(final String key) {
        Object result = null;
        ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
        result = operations.get(key);
        return result;
    }

    /**
     * 根据key pattern读取缓存
     *
     * @param pattern
     * @return List<Object>
     */
    public List<Object> multiGet(final String pattern) {
        ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
        Set keys = redisTemplate.keys(pattern);
        return operations.multiGet(keys);
    }

    /**
     * 刷新缓存
     *
     * @param key
     * @param expireTime
     * @return boolean
     */
    public  boolean refreshExpireTime(final String key, int expireTime) {
        return redisTemplate.expire(key, expireTime, TimeUnit.SECONDS);
    }

    /**
     * 写入缓存
     *
     * @param key
     * @param value
     * @return
     */
    public boolean set(final String key, Object value) {
        boolean result = false;
        try {
            ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
            operations.set(key, value);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 写入缓存 (秒级别)
     *
     * @param key
     * @param value
     * @return
     */
    public boolean set(final String key, Object value, int expireTime) {
        boolean result = false;
        try {
            ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
            operations.set(key, value);
            redisTemplate.expire(key, expireTime, TimeUnit.SECONDS);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }


}

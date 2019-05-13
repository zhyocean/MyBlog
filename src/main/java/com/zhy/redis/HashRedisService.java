package com.zhy.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;

/**
 * @author: zhangocean
 * @Date: 2019/5/12 16:10
 * Describe: Hash类型redis操作
 */
@Service
public class HashRedisService {

    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    /**
     * 设置key和value
     * @param key key值
     * @param value 要设置的value值
     * @param entityClass 要设置的value值的Class
     */
    public void put(String key, Object value, Class<?> entityClass){
        HashOperations<String, Object, Object> vo = redisTemplate.opsForHash();
        //通过反射获得对象属性
        Field[] fields = entityClass.getDeclaredFields();
        for(Field field : fields){
            field.setAccessible(true);
            //保存对象属性和值到对应key值下
            try {
                vo.put(key, field.getName(), field.get(value));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 获得对应key值下的属性field的值
     * @param key key值
     * @param field 要获得值的属性
     * @return 如果存在则返回值，否则返回null
     */
    public Object get(String key, Object field){
        HashOperations<String, Object, Object> vo = redisTemplate.opsForHash();
        return vo.get(key, field);
    }

    /**
     * 获得key的所有值
     */
    public Object hashGetAll(String key){
        return redisTemplate.opsForHash().entries(key);
    }

    /**
     * 删除指定key
     */
    public void hashDelete(String key, Class<?> entityClass){
        Field[] fields = entityClass.getDeclaredFields();
        for(Field field : fields){
            redisTemplate.opsForHash().delete(key, field.getName());
        }
    }

    /**
     * key的指定字段的整数值加上增量increment
     */
    public Long hashIncrement(String key, Object field, long increment) {
        return redisTemplate.opsForHash().increment(key, field, increment);
    }

    /**
     * 判断key是否存在
     */
    public Boolean hasKey(String key){
        return redisTemplate.opsForHash().getOperations().hasKey(key);
    }

}

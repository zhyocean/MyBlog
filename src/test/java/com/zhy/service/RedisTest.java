package com.zhy.service;

import com.zhy.model.UserReadNews;
import com.zhy.redis.HashRedisService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.LinkedHashMap;

/**
 * @author: zhangocean
 * @Date: 2019/5/6 14:30
 * Describe:
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class RedisTest {

    @Autowired
    private HashRedisService hashRedisService;

    @Test
    public void redisTest() throws IllegalAccessException {

        LinkedHashMap news = (LinkedHashMap) hashRedisService.hashGetAll("3");

    }

}

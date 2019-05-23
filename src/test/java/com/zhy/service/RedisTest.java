package com.zhy.service;

import com.zhy.mapper.CategoryMapper;
import com.zhy.mapper.FriendLinkMapper;
import com.zhy.mapper.VisitorMapper;
import com.zhy.model.FriendLink;
import com.zhy.redis.HashRedisServiceImpl;
import com.zhy.redis.StringRedisServiceImpl;
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
    HashRedisServiceImpl hashRedisService;
    @Autowired
    VisitorMapper visitorMapper;

    @Test
    public void redisTest(){
    }

}

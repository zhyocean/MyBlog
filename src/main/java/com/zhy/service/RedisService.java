package com.zhy.service;

import com.zhy.model.UserReadNews;
import com.zhy.redis.HashRedisService;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;

/**
 * @author: zhangocean
 * @Date: 2019/5/12 21:29
 * Describe: redis业务逻辑
 */
@Service
public class RedisService {

    @Autowired
    HashRedisService hashRedisService;
    @Autowired
    UserService userService;

    public JSONObject getUserNews(String username) {
        JSONObject jsonObject = new JSONObject();

        int userId = userService.findIdByUsername(username);
        LinkedHashMap map = (LinkedHashMap) hashRedisService.hashGetAll(String.valueOf(userId));
        if(map.size() == 0){
            jsonObject.put("result", 0);
        } else {
            int allNewNum = (int) map.get("allNewsNum");
            int commentNum = (int) map.get("commentNum");
            int leaveMessageNum = (int) map.get("leaveMessageNum");
            UserReadNews news = new UserReadNews(allNewNum, commentNum, leaveMessageNum);
            jsonObject.put("result", news);
        }
        jsonObject.put("status", "200");
        return jsonObject;
    }

    public void readOneMsgOnRedis(int userId, int msgType) {
        LinkedHashMap map = (LinkedHashMap) hashRedisService.hashGetAll(String.valueOf(userId));
        int allNewsNum = (int) map.get("allNewsNum");
        hashRedisService.hashIncrement(String.valueOf(userId), "allNewsNum", -1);
        //如果总留言评论数为0则删除该key
        if(--allNewsNum == 0){
            hashRedisService.hashDelete(String.valueOf(userId), UserReadNews.class);
        } else if (msgType == 1){
            hashRedisService.hashIncrement(String.valueOf(userId), "commentNum", -1);
        } else {
            hashRedisService.hashIncrement(String.valueOf(userId), "leaveMessageNum", -1);
        }
    }

    public void readAllMsgOnRedis(int userId, int msgType) {
        LinkedHashMap map = (LinkedHashMap) hashRedisService.hashGetAll(String.valueOf(userId));
        int commentNum = (int) map.get("commentNum");
        int leaveMessageNum = (int) map.get("leaveMessageNum");
        if(commentNum == 0 || leaveMessageNum == 0){
            hashRedisService.hashDelete(String.valueOf(userId), UserReadNews.class);
        } else if (msgType == 1){
            hashRedisService.hashIncrement(String.valueOf(userId), "allNewsNum", -commentNum);
            hashRedisService.hashIncrement(String.valueOf(userId), "commentNum", -commentNum);
        } else {
            hashRedisService.hashIncrement(String.valueOf(userId), "allNewsNum", -leaveMessageNum);
            hashRedisService.hashIncrement(String.valueOf(userId), "leaveMessageNum", -leaveMessageNum);
        }
    }

}

package com.zhy.component;

import com.zhy.redis.HashRedisServiceImpl;
import com.zhy.service.VisitorService;
import com.zhy.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;

/**
 * @author: zhangocean
 * @Date: 2019/5/22 13:22
 * Describe: 定时任务
 */
@Component
public class ScheduledTask {

    @Autowired
    HashRedisServiceImpl hashRedisService;
    @Autowired
    VisitorService visitorService;

    /**
     * cron表达式生成器：http://cron.qqe2.com/
     *
     * 每晚24点清空redis中当日网站访问记录，但保存totalVisitor、visitorVolume、yesterdayVisitor
     */
    @Scheduled(cron = "0 0 0 * * ? ")
    public void resetVisitorNumber(){
        long oldTotalVisitor = visitorService.getTotalVisitor();
        long newTotalVisitor = Long.valueOf(hashRedisService.get(StringUtil.VISITOR, "totalVisitor").toString());
        long yesterdayVisitor = newTotalVisitor - oldTotalVisitor;
        if(hashRedisService.hasHashKey(StringUtil.VISITOR, "yesterdayVisitor")){
            hashRedisService.put(StringUtil.VISITOR, "yesterdayVisitor", yesterdayVisitor);
        } else {
            hashRedisService.put(StringUtil.VISITOR, "yesterdayVisitor", oldTotalVisitor);
        }
        //将redis中的所有访客记录更新到数据库中
        LinkedHashMap map = (LinkedHashMap) hashRedisService.getAllFieldAndValue(StringUtil.VISITOR);
        String pageName;
        for(Object e : map.keySet()){
            pageName = String.valueOf(e);
            visitorService.updateVisitorNumByPageName(pageName, String.valueOf(map.get(e)));
            if(!"totalVisitor".equals(pageName) && !"visitorVolume".equals(pageName) && !"yesterdayVisitor".equals(pageName)){
                hashRedisService.hashDelete(StringUtil.VISITOR, pageName);
            }
        }
    }

}

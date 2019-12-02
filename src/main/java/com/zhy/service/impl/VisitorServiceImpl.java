package com.zhy.service.impl;

import com.zhy.mapper.VisitorMapper;
import com.zhy.service.RedisService;
import com.zhy.service.VisitorService;
import com.zhy.utils.DataMap;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

/**
 * @author: zhangocean
 * @Date: 2018/6/16 16:21
 * Describe: 访客实现类
 */
@Service
public class VisitorServiceImpl implements VisitorService {

    private static final String VISITOR = "visitor";
    private static final String TOTAL_VISITOR = "totalVisitor";
    private static final String PAGE_VISITOR = "pageVisitor";

    @Autowired
    VisitorMapper visitorMapper;
    @Autowired
    RedisService redisService;

    @Override
    public DataMap addVisitorNumByPageName(String pageName, HttpServletRequest request) {

        String visitor;
        Long pageVisitor;
        JSONObject jsonObject = new JSONObject();

        visitor = (String) request.getSession().getAttribute(pageName);
        if(visitor == null){
            //先去redis中查找
            pageVisitor = redisService.addVisitorNumOnRedis(VISITOR, pageName, 1);
            if(pageVisitor == null){
                //redis中未命中则从数据库中获得
                pageVisitor = visitorMapper.getVisitorNumByPageName(pageName);
                pageVisitor = redisService.putVisitorNumOnRedis(VISITOR, pageName, pageVisitor+1);
            }
            //在session中保存该用户访问页面的记录，在一段时间内重复访问时不增加在页面的访问人次
            request.getSession().setAttribute(pageName,"yes");
        } else {
            pageVisitor = redisService.addVisitorNumOnRedis(VISITOR, pageName, 0);
            if(pageVisitor == null){
                //redis中未命中则从数据库中获得
                pageVisitor = visitorMapper.getVisitorNumByPageName(pageName);
                pageVisitor = redisService.putVisitorNumOnRedis(VISITOR, pageName, pageVisitor);
            }
        }

        //增加总访问人数
        Long totalVisitor = redisService.addVisitorNumOnRedis(VISITOR, TOTAL_VISITOR, 1);
        if(totalVisitor == null){
            totalVisitor = visitorMapper.getTotalVisitor();
            totalVisitor = redisService.putVisitorNumOnRedis(VISITOR, TOTAL_VISITOR, totalVisitor+1);
        }

        jsonObject.put(TOTAL_VISITOR, totalVisitor);
        jsonObject.put(PAGE_VISITOR, pageVisitor);
        return DataMap.success().setData(jsonObject);
    }

    @Override
    public long getNumByPageName(String pageName) {
        return visitorMapper.getVisitorNumByPageName(pageName);
    }

    @Override
    public void insertVisitorArticlePage(String pageName) {
        visitorMapper.save(pageName);
    }

    @Override
    public long getTotalVisitor() {
        return visitorMapper.getTotalVisitor();
    }

    @Override
    public void updateVisitorNumByPageName(String pageName, String visitorNum) {
        visitorMapper.updateVisitorNumByPageName(pageName, visitorNum);
    }

}

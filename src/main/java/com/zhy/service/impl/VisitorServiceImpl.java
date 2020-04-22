package com.zhy.service.impl;

import com.zhy.mapper.VisitorMapper;
import com.zhy.model.Visitor;
import com.zhy.service.RedisService;
import com.zhy.service.VisitorService;
import com.zhy.utils.DataMap;
import com.zhy.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: zhangocean
 * @Date: 2018/6/16 16:21
 * Describe: 访客实现类
 */
@Service
public class VisitorServiceImpl implements VisitorService {

    private static final String TOTAL_VISITOR = "totalVisitor";
    private static final String PAGE_VISITOR = "pageVisitor";

    @Autowired
    VisitorMapper visitorMapper;
    @Autowired
    RedisService redisService;

    @Override
    public DataMap addVisitorNumByPageName(String pageName, HttpServletRequest request) {
        Map<String, Object> dataMap = new HashMap<>(2);

        String visitor = (String) request.getSession().getAttribute(pageName);

        //Session生命周期内没有浏览过该page，则增加访客量或文章访问人数
        if(visitor == null){
            request.getSession().setAttribute(pageName,"yes");
        }

        //增加当前页访问人数
        Long pageVisitor = updatePageVisitor(visitor, pageName);

        //增加总访问人数
        Long totalVisitor = redisService.addVisitorNumOnRedis(StringUtil.VISITOR, TOTAL_VISITOR, 1);
        if(totalVisitor == null){
            totalVisitor = visitorMapper.getTotalVisitor();
            totalVisitor = redisService.putVisitorNumOnRedis(StringUtil.VISITOR, TOTAL_VISITOR, totalVisitor+1);
        }

        dataMap.put(TOTAL_VISITOR, totalVisitor);
        dataMap.put(PAGE_VISITOR, pageVisitor);
        return DataMap.success().setData(dataMap);
    }

    private Long updatePageVisitor(String visitor, String pageName){
        Visitor thisVisitor;
        Long pageVisitor;

        //Session生命周期内没有浏览过该page，则增加访客量或文章访问人数
        if(visitor == null){
            pageVisitor = redisService.addVisitorNumOnRedis(StringUtil.VISITOR, pageName, 1);
            if(pageVisitor == null){
                //redis中未命中则从数据库中获得
                thisVisitor = visitorMapper.getVisitorNumByPageName(pageName);
                if(thisVisitor != null){
                    pageVisitor = thisVisitor.getVisitorNum();
                    redisService.putVisitorNumOnRedis(StringUtil.VISITOR, pageName, pageVisitor+1);
                } else {
                    return 0L;
                }
            }
        }

        pageVisitor = redisService.getVisitorNumOnRedis(StringUtil.VISITOR, pageName);
        //由于redis中的访客信息会在定时任务中定时持久化到数据库中
        //可能出现Session未过期，但此时redis中数据持久化了，就需要重新去数据库中进行查询
        if(pageVisitor == null){
            thisVisitor = visitorMapper.getVisitorNumByPageName(pageName);
            if(thisVisitor != null){
                pageVisitor = thisVisitor.getVisitorNum();
                pageVisitor = redisService.putVisitorNumOnRedis(StringUtil.VISITOR, pageName, pageVisitor);
            } else {
                return 0L;
            }
        }

        return pageVisitor;
    }

    @Override
    public long getNumByPageName(String pageName) {
        Visitor visitor = visitorMapper.getVisitorNumByPageName(pageName);
        if(visitor != null){
            return visitor.getVisitorNum();
        }
        return 0L;
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

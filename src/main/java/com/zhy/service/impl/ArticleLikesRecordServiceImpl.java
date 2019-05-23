package com.zhy.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zhy.mapper.ArticleLikesMapper;
import com.zhy.model.ArticleLikesRecord;
import com.zhy.redis.StringRedisServiceImpl;
import com.zhy.service.ArticleLikesRecordService;
import com.zhy.service.ArticleService;
import com.zhy.service.UserService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: zhangocean
 * @Date: 2018/7/7 15:50
 * Describe:
 */
@Service
public class ArticleLikesRecordServiceImpl implements ArticleLikesRecordService {

    @Autowired
    ArticleLikesMapper articleLikesMapper;
    @Autowired
    UserService userService;
    @Autowired
    ArticleService articleService;
    @Autowired
    StringRedisServiceImpl stringRedisService;

    @Override
    public boolean isLiked(long articleId, String username) {
        ArticleLikesRecord articleLikesRecord = articleLikesMapper.isLiked(articleId, userService.findIdByUsername(username));

        return articleLikesRecord != null;
    }

    @Override
    public void insertArticleLikesRecord(ArticleLikesRecord articleLikesRecord) {
        articleLikesMapper.insertArticleLikesRecord(articleLikesRecord);
    }

    @Override
    public void deleteArticleLikesRecordByArticleId(long articleId) {
        articleLikesMapper.deleteArticleLikesRecordByArticleId(articleId);
    }

    @Override
    public JSONObject getArticleThumbsUp(String username, int rows, int pageNum) {
        JSONObject returnJson = new JSONObject();

        int likerId = userService.findIdByUsername(username);
        PageHelper.startPage(pageNum, rows);
        List<ArticleLikesRecord> likesRecords = articleLikesMapper.getArticleThumbsUp(likerId);
        PageInfo<ArticleLikesRecord> pageInfo = new PageInfo<>(likesRecords);
        JSONArray returnJsonArray = new JSONArray();
        JSONObject articleLikesJson;
        for(ArticleLikesRecord a : likesRecords){
            articleLikesJson = new JSONObject();
            articleLikesJson.put("id", a.getId());
            articleLikesJson.put("articleId", a.getArticleId());
            articleLikesJson.put("likeDate", a.getLikeDate());
            articleLikesJson.put("praisePeople", userService.findUsernameById(a.getLikerId()));
            articleLikesJson.put("articleTitle", articleService.findArticleTitleByArticleId(a.getArticleId()).get("articleTitle"));
            articleLikesJson.put("isRead", a.getIsRead());
            returnJsonArray.add(articleLikesJson);
        }
        returnJson.put("status", 200);
        returnJson.put("result", returnJsonArray);
        returnJson.put("msgIsNotReadNum",articleLikesMapper.countIsReadNum());

        JSONObject pageJson = new JSONObject();
        pageJson.put("pageNum",pageInfo.getPageNum());
        pageJson.put("pageSize",pageInfo.getPageSize());
        pageJson.put("total",pageInfo.getTotal());
        pageJson.put("pages",pageInfo.getPages());
        pageJson.put("isFirstPage",pageInfo.isIsFirstPage());
        pageJson.put("isLastPage",pageInfo.isIsLastPage());
        returnJson.put("pageInfo",pageJson);

        return returnJson;
    }

    @Override
    public int readThisThumbsUp(int id) {
        try {
            articleLikesMapper.readThisThumbsUp(id);
            stringRedisService.stringIncrement("articleThumbsUp",-1);
            int articleThumbsUp = (int) stringRedisService.get("articleThumbsUp");
            if(articleThumbsUp == 0){
                stringRedisService.remove("articleThumbsUp");
            }
            return 1;
        } catch (Exception e){
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public JSONObject readAllThumbsUp() {
        JSONObject jsonObject = new JSONObject();
        articleLikesMapper.readAllThumbsUp();
        stringRedisService.remove("articleThumbsUp");
        jsonObject.put("status", 200);
        return jsonObject;
    }

}

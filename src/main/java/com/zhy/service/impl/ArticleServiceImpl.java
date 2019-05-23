package com.zhy.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zhy.component.StringAndArray;
import com.zhy.constant.SiteOwner;
import com.zhy.mapper.ArticleMapper;
import com.zhy.model.Article;
import com.zhy.model.ArticleLikesRecord;
import com.zhy.service.*;
import com.zhy.utils.TimeUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Time;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: zhangocean
 * @Date: 2018/6/20 21:42
 * Describe:
 */
@Service
public class ArticleServiceImpl implements ArticleService {

    private Logger logger = LoggerFactory.getLogger(ArticleServiceImpl.class);

    @Autowired
    private ArticleMapper articleMapper;
    @Autowired
    private ArticleService articleService;
    @Autowired
    private ArticleLikesRecordService articleLikesRecordService;
    @Autowired
    private VisitorService visitorService;
    @Autowired
    private ArchiveService archiveService;
    @Autowired
    private CommentService commentService;
    @Autowired
    private CommentLikesRecordService commentLikesRecordService;

    @Override
    public JSONObject insertArticle(Article article) {
        JSONObject articleReturn = new JSONObject();

        try {
            if("".equals(article.getOriginalAuthor())){
                article.setOriginalAuthor(article.getAuthor());
            }
            if("".equals(article.getArticleUrl())){
                //保存文章的url
                String url = SiteOwner.SITE_OWNER_URL + "/article/" + article.getArticleId();
                article.setArticleUrl(url);
            }
            Article endArticleId = articleMapper.findEndArticleId();
            //设置文章的上一篇文章id
            if(endArticleId != null){
                article.setLastArticleId(endArticleId.getArticleId());
            }
            articleMapper.insertArticle(article);
            //判断发表文章的归档日期是否存在，不存在则插入归档日期
            TimeUtil timeUtil = new TimeUtil();
            String archiveName = timeUtil.timeWhippletreeToYear(article.getPublishDate().substring(0, 7));
            archiveService.addArchiveName(archiveName);
            //新文章加入访客量
            visitorService.insertVisitorArticlePage("article/" + article.getArticleId());
            //设置上一篇文章的下一篇文章id
            if(endArticleId != null){
                articleService.updateArticleLastOrNextId("nextArticleId", article.getArticleId(), endArticleId.getArticleId());
            }

            articleReturn.put("status",200);
            articleReturn.put("articleTitle",article.getArticleTitle());
            articleReturn.put("updateDate",article.getUpdateDate());
            articleReturn.put("author",article.getOriginalAuthor());
            //本博客中的URL
            articleReturn.put("articleUrl","/article/" + article.getArticleId());
            return articleReturn;
        } catch (Exception e){
            articleReturn.put("status",500);
            logger.error("用户 " + article.getAuthor() + " 保存文章 " + article.getArticleTitle() + " 失败");
            e.printStackTrace();
            return articleReturn;
        }
    }

    @Override
    public JSONObject updateArticleById(Article article) {
        Article a = articleMapper.getArticleUrlById(article.getId());
        if("原创".equals(article.getArticleType())){
            article.setOriginalAuthor(article.getAuthor());
            String url = SiteOwner.SITE_OWNER_URL + "/article/" + a.getArticleId();
            article.setArticleUrl(url);
        }
        articleMapper.updateArticleById(article);
        JSONObject articleReturn = new JSONObject();
        articleReturn.put("status",200);
        articleReturn.put("articleTitle",article.getArticleTitle());
        articleReturn.put("updateDate",article.getUpdateDate());
        articleReturn.put("author",article.getOriginalAuthor());
        //本博客中的URL
        articleReturn.put("articleUrl","/article/" + a.getArticleId());
        return articleReturn;
    }

    @Override
    public JSONObject getArticleByArticleId(long articleId, String username) {
        Article article = articleMapper.getArticleByArticleId(articleId);

        JSONObject jsonObject = new JSONObject();
        if(article != null){
            Article lastArticle = articleMapper.findArticleByArticleId(article.getLastArticleId());
            Article nextArticle = articleMapper.findArticleByArticleId(article.getNextArticleId());
            jsonObject.put("status","200");
            jsonObject.put("author",article.getAuthor());
            jsonObject.put("articleId",articleId);
            jsonObject.put("originalAuthor",article.getOriginalAuthor());
            jsonObject.put("articleTitle",article.getArticleTitle());
            jsonObject.put("publishDate",article.getPublishDate());
            jsonObject.put("updateDate",article.getUpdateDate());
            jsonObject.put("articleContent",article.getArticleContent());
            jsonObject.put("articleTags", StringAndArray.stringToArray(article.getArticleTags()));
            jsonObject.put("articleType",article.getArticleType());
            jsonObject.put("articleCategories",article.getArticleCategories());
            jsonObject.put("articleUrl",article.getArticleUrl());
            jsonObject.put("likes",article.getLikes());
            if(username == null){
                jsonObject.put("isLiked",0);
            }else {
                if(articleLikesRecordService.isLiked(articleId,username)){
                    jsonObject.put("isLiked",1);
                }else {
                    jsonObject.put("isLiked",0);
                }
            }
            if(lastArticle != null){
                jsonObject.put("lastStatus","200");
                jsonObject.put("lastArticleTitle",lastArticle.getArticleTitle());
                jsonObject.put("lastArticleUrl","/article/" + lastArticle.getArticleId());
            } else {
                jsonObject.put("lastStatus","500");
                jsonObject.put("lastInfo","无");
            }
            if(nextArticle != null){
                jsonObject.put("nextStatus","200");
                jsonObject.put("nextArticleTitle",nextArticle.getArticleTitle());
                jsonObject.put("nextArticleUrl","/article/" + nextArticle.getArticleId());
            } else {
                jsonObject.put("nextStatus","500");
                jsonObject.put("nextInfo","无");
            }
            return jsonObject;
        } else {
            jsonObject.put("status","500");
            jsonObject.put("errorInfo","获取文章信息失败");
            logger.error("获取文章id " + articleId + " 失败");
            return jsonObject;
        }
    }

    @Override
    public Map<String,String> findArticleTitleByArticleId(long articleId) {
        Article articleInfo = articleMapper.findArticleTitleByArticleId(articleId);
        Map<String, String> articleMap = new HashMap<>();
        if(articleInfo != null){
            articleMap.put("articleTitle", articleInfo.getArticleTitle());
            articleMap.put("articleTabloid", articleInfo.getArticleTabloid());
        }
        return articleMap;
    }

    @Override
    public JSONArray findAllArticles(String rows, String pageNo) {
        int pageNum = Integer.parseInt(pageNo);
        int pageSize = Integer.parseInt(rows);

        PageHelper.startPage(pageNum,pageSize);
        List<Article> articles = articleMapper.findAllArticles();
        PageInfo<Article> pageInfo = new PageInfo<>(articles);
        List<Map<String, Object>> newArticles = new ArrayList<>();
        Map<String, Object> map;

        for(Article article : articles){
            map = new HashMap<>();
            map.put("thisArticleUrl", "/article/" + article.getArticleId());
            map.put("articleTags",StringAndArray.stringToArray(article.getArticleTags()));
            map.put("articleTitle", article.getArticleTitle());
            map.put("articleType", article.getArticleType());
            map.put("publishDate", article.getPublishDate());
            map.put("originalAuthor", article.getOriginalAuthor());
            map.put("articleCategories", article.getArticleCategories());
            map.put("articleTabloid", article.getArticleTabloid());
            map.put("likes", article.getLikes());
            newArticles.add(map);
        }
        JSONArray jsonArray = JSONArray.fromObject(newArticles);
        Map<String, Object> thisPageInfo = new HashMap<>();
        thisPageInfo.put("pageNum",pageInfo.getPageNum());
        thisPageInfo.put("pageSize",pageInfo.getPageSize());
        thisPageInfo.put("total",pageInfo.getTotal());
        thisPageInfo.put("pages",pageInfo.getPages());
        thisPageInfo.put("isFirstPage",pageInfo.isIsFirstPage());
        thisPageInfo.put("isLastPage",pageInfo.isIsLastPage());

        jsonArray.add(thisPageInfo);
        return jsonArray;
    }

    @Override
    public void updateArticleLastOrNextId(String lastOrNext, long lastOrNextArticleId, long articleId) {
        if("lastArticleId".equals(lastOrNext)){
            articleMapper.updateArticleLastId(lastOrNextArticleId, articleId);
        }
        if("nextArticleId".equals(lastOrNext)){
            articleMapper.updateArticleNextId(lastOrNextArticleId, articleId);
        }
    }

    @Override
    public int updateLikeByArticleId(long articleId) {

        articleMapper.updateLikeByArticleId(articleId);
        return articleMapper.findLikesByArticleId(articleId);
    }

    @Override
    public JSONObject findArticleByTag(String tag, int rows, int pageNum) {

        PageHelper.startPage(pageNum, rows);
        List<Article> articles = articleMapper.findArticleByTag(tag);
        PageInfo<Article> pageInfo = new PageInfo<>(articles);
        JSONObject articleJson;
        JSONArray articleJsonArray = new JSONArray();
        //二次判断标签是否匹配
        for(Article article : articles){
            String[] tagsArray = StringAndArray.stringToArray(article.getArticleTags());
            for(String str : tagsArray){
                if(str.equals(tag)){
                    articleJson = new JSONObject();
                    articleJson.put("articleId", article.getArticleId());
                    articleJson.put("originalAuthor", article.getOriginalAuthor());
                    articleJson.put("articleTitle", article.getArticleTitle());
                    articleJson.put("articleCategories", article.getArticleCategories());
                    articleJson.put("publishDate", article.getPublishDate());
                    articleJson.put("articleTags", tagsArray);
                    articleJsonArray.add(articleJson);
                }
            }
        }

        JSONObject pageJson = new JSONObject();
        pageJson.put("pageNum",pageInfo.getPageNum());
        pageJson.put("pageSize",pageInfo.getPageSize());
        pageJson.put("total",pageInfo.getTotal());
        pageJson.put("pages",pageInfo.getPages());
        pageJson.put("isFirstPage",pageInfo.isIsFirstPage());
        pageJson.put("isLastPage",pageInfo.isIsLastPage());

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("status",201);
        jsonObject.put("result",articleJsonArray);
        jsonObject.put("tag",tag);
        jsonObject.put("pageInfo",pageJson);
        return jsonObject;
    }

    @Override
    public JSONObject findArticleByCategory(String category, int rows, int pageNum) {

        List<Article> articles;
        PageInfo<Article> pageInfo;
        JSONArray articleJsonArray = new JSONArray();
        PageHelper.startPage(pageNum, rows);
        if("".equals(category)){
            articles = articleMapper.findAllArticlesPartInfo();
            category = "全部分类";
        } else {
            articles = articleMapper.findArticleByCategory(category);
        }
        pageInfo = new PageInfo<>(articles);

        articleJsonArray = timeLineReturn(articleJsonArray, articles);

        JSONObject pageJson = new JSONObject();
        pageJson.put("pageNum",pageInfo.getPageNum());
        pageJson.put("pageSize",pageInfo.getPageSize());
        pageJson.put("total",pageInfo.getTotal());
        pageJson.put("pages",pageInfo.getPages());
        pageJson.put("isFirstPage",pageInfo.isIsFirstPage());
        pageJson.put("isLastPage",pageInfo.isIsLastPage());

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("status",200);
        jsonObject.put("result",articleJsonArray);
        jsonObject.put("category",category);
        jsonObject.put("pageInfo",pageJson);

        return jsonObject;
    }

    @Override
    public JSONObject findArticleByArchive(String archive, int rows, int pageNum) {
        List<Article> articles;
        PageInfo<Article> pageInfo;
        JSONArray articleJsonArray = new JSONArray();
        TimeUtil timeUtil = new TimeUtil();
        String showMonth = "hide";
        if(!"".equals(archive)){
            archive = timeUtil.timeYearToWhippletree(archive);
        }
        PageHelper.startPage(pageNum, rows);
        if("".equals(archive)){
            articles = articleMapper.findAllArticlesPartInfo();
        } else {
            articles = articleMapper.findArticleByArchive(archive);
            showMonth = "show";
        }
        pageInfo = new PageInfo<>(articles);

        articleJsonArray = timeLineReturn(articleJsonArray, articles);

        JSONObject pageJson = new JSONObject();
        pageJson.put("pageNum",pageInfo.getPageNum());
        pageJson.put("pageSize",pageInfo.getPageSize());
        pageJson.put("total",pageInfo.getTotal());
        pageJson.put("pages",pageInfo.getPages());
        pageJson.put("isFirstPage",pageInfo.isIsFirstPage());
        pageJson.put("isLastPage",pageInfo.isIsLastPage());

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("status",200);
        jsonObject.put("result",articleJsonArray);
        jsonObject.put("pageInfo",pageJson);
        jsonObject.put("articleNum", articleMapper.countArticle());
        jsonObject.put("showMonth", showMonth);

        return jsonObject;
    }

    @Override
    public JSONObject getDraftArticle(Article article, String[] articleTags, int articleGrade) {
        JSONObject returnJson = new JSONObject();
        returnJson.put("id", article.getId());
        returnJson.put("articleTitle", article.getArticleTitle());
        returnJson.put("articleType", article.getArticleType());
        returnJson.put("articleCategories", article.getArticleCategories());
        returnJson.put("articleUrl", article.getArticleUrl());
        returnJson.put("originalAuthor", article.getOriginalAuthor());
        returnJson.put("articleContent", article.getArticleContent());
        returnJson.put("articleTags", articleTags);
        returnJson.put("articleGrade", articleGrade);
        return returnJson;
    }

    @Override
    public JSONObject getArticleManagement(int rows, int pageNum) {
        PageHelper.startPage(pageNum, rows);
        List<Article> articles = articleMapper.getArticleManagement();
        PageInfo<Article> pageInfo = new PageInfo<>(articles);
        JSONArray returnJsonArray = new JSONArray();
        JSONObject returnJson = new JSONObject();
        JSONObject articleJson;
        for(Article article : articles){
            articleJson = new JSONObject();
            articleJson.put("id",article.getId());
            articleJson.put("articleId",article.getArticleId());
            articleJson.put("originalAuthor",article.getOriginalAuthor());
            articleJson.put("articleTitle",article.getArticleTitle());
            articleJson.put("articleCategories",article.getArticleCategories());
            articleJson.put("publishDate",article.getPublishDate());
            String pageName = "article/" + article.getArticleId();
            articleJson.put("visitorNum",visitorService.getNumByPageName(pageName));

            returnJsonArray.add(articleJson);
        }
        returnJson.put("status",200);
        returnJson.put("result",returnJsonArray);
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
    public Article findArticleById(int id) {
        return articleMapper.findArticleById(id);
    }


    @Override
    public int countArticleCategoryByCategory(String category) {
        return articleMapper.countArticleCategoryByCategory(category);
    }

    @Override
    public int countArticleArchiveByArchive(String archive) {
        return articleMapper.countArticleArchiveByArchive(archive);
    }

    @Override
    public int countArticle() {
        return articleMapper.countArticle();
    }

    @Override
    public int deleteArticle(long id) {
        try {
            Article deleteArticle = articleMapper.findAllArticleId(id);
            articleMapper.updateLastOrNextId("lastArticleId", deleteArticle.getLastArticleId(), deleteArticle.getNextArticleId());
            articleMapper.updateLastOrNextId("nextArticleId", deleteArticle.getNextArticleId(), deleteArticle.getLastArticleId());
            //删除本篇文章
            articleMapper.deleteByArticleId(deleteArticle.getArticleId());
            //删除与该文章有关的所有文章点赞记录、文章评论、文章评论记录
            commentService.deleteCommentByArticleId(deleteArticle.getArticleId());
            commentLikesRecordService.deleteCommentLikesRecordByArticleId(deleteArticle.getArticleId());
            articleLikesRecordService.deleteArticleLikesRecordByArticleId(deleteArticle.getArticleId());
        }catch (Exception e){
            logger.error("删除文章失败，文章id=" + id);
            return 0;
        }
        return 1;
    }

    /**
     * 封装时间线中数据成JsonArray形式
     */
    private JSONArray timeLineReturn(JSONArray articleJsonArray, List<Article> articles){
        JSONObject articleJson;
        for(Article article : articles){
            String[] tagsArray = StringAndArray.stringToArray(article.getArticleTags());
            articleJson = new JSONObject();
            articleJson.put("articleId", article.getArticleId());
            articleJson.put("originalAuthor", article.getOriginalAuthor());
            articleJson.put("articleTitle", article.getArticleTitle());
            articleJson.put("articleCategories", article.getArticleCategories());
            articleJson.put("publishDate", article.getPublishDate());
            articleJson.put("articleTags", tagsArray);
            articleJsonArray.add(articleJson);
        }
        return articleJsonArray;
    }

}

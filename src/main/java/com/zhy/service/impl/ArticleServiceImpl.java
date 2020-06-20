package com.zhy.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zhy.component.StringAndArray;
import com.zhy.constant.CodeType;
import com.zhy.constant.SiteOwner;
import com.zhy.mapper.ArticleMapper;
import com.zhy.model.Article;
import com.zhy.service.*;
import com.zhy.utils.DataMap;
import com.zhy.utils.StringUtil;
import com.zhy.utils.TimeUtil;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
@Slf4j
public class ArticleServiceImpl implements ArticleService {

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
    public DataMap insertArticle(Article article) {
        Map<String, Object> dataMap = new HashMap<>(4);

        if(StringUtil.BLANK.equals(article.getOriginalAuthor())){
            article.setOriginalAuthor(article.getAuthor());
        }
        if(StringUtil.BLANK.equals(article.getArticleUrl())){
            //保存文章的url
            String url = SiteOwner.SITE_OWNER_URL + "/article/" + article.getArticleId();
            article.setArticleUrl(url);
        }
        Article endArticleId = articleMapper.findEndArticleId();
        //设置文章的上一篇文章id
        if(endArticleId != null){
            article.setLastArticleId(endArticleId.getArticleId());
        }
        articleMapper.save(article);
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

        dataMap.put("articleTitle",article.getArticleTitle());
        dataMap.put("updateDate",article.getUpdateDate());
        dataMap.put("author",article.getOriginalAuthor());
        dataMap.put("articleUrl","/article/" + article.getArticleId());
        return DataMap.success().setData(dataMap);
    }

    @Override
    public DataMap updateArticleById(Article article) {
        Article a = articleMapper.getArticleUrlById(article.getId());
        if("原创".equals(article.getArticleType())){
            article.setOriginalAuthor(article.getAuthor());
            String url = SiteOwner.SITE_OWNER_URL + "/article/" + a.getArticleId();
            article.setArticleUrl(url);
        }
        articleMapper.updateArticleById(article);
        Map<String, Object> dataMap = new HashMap<>(4);
        dataMap.put("articleTitle",article.getArticleTitle());
        dataMap.put("updateDate",article.getUpdateDate());
        dataMap.put("author",article.getOriginalAuthor());
        dataMap.put("articleUrl","/article/" + a.getArticleId());

        return DataMap.success().setData(dataMap);
    }

    @Override
    public DataMap getArticleByArticleId(long articleId, String username) {
        Article article = articleMapper.getArticleByArticleId(articleId);

        if(article != null){
            Map<String, Object> dataMap = new HashMap<>(32);
            Article lastArticle = articleMapper.findArticleByArticleId(article.getLastArticleId());
            Article nextArticle = articleMapper.findArticleByArticleId(article.getNextArticleId());
            dataMap.put("author",article.getAuthor());
            dataMap.put("articleId",articleId);
            dataMap.put("originalAuthor",article.getOriginalAuthor());
            dataMap.put("articleTitle",article.getArticleTitle());
            dataMap.put("publishDate",article.getPublishDate());
            dataMap.put("updateDate",article.getUpdateDate());
            dataMap.put("articleContent",article.getArticleContent());
            dataMap.put("articleTags", StringAndArray.stringToArray(article.getArticleTags()));
            dataMap.put("articleType",article.getArticleType());
            dataMap.put("articleCategories",article.getArticleCategories());
            dataMap.put("articleUrl",article.getArticleUrl());
            dataMap.put("likes",article.getLikes());
            if(username == null){
                dataMap.put("isLiked",0);
            }else {
                if(articleLikesRecordService.isLiked(articleId,username)){
                    dataMap.put("isLiked",1);
                }else {
                    dataMap.put("isLiked",0);
                }
            }
            if(lastArticle != null){
                dataMap.put("lastStatus","200");
                dataMap.put("lastArticleTitle",lastArticle.getArticleTitle());
                dataMap.put("lastArticleUrl","/article/" + lastArticle.getArticleId());
            } else {
                dataMap.put("lastStatus","500");
                dataMap.put("lastInfo","无");
            }
            if(nextArticle != null){
                dataMap.put("nextStatus","200");
                dataMap.put("nextArticleTitle",nextArticle.getArticleTitle());
                dataMap.put("nextArticleUrl","/article/" + nextArticle.getArticleId());
            } else {
                dataMap.put("nextStatus","500");
                dataMap.put("nextInfo","无");
            }
            return DataMap.success().setData(dataMap);
        }

        return DataMap.fail(CodeType.ARTICLE_NOT_EXIST);
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
    public DataMap findAllArticles(int rows, int pageNum) {

        PageHelper.startPage(pageNum,rows);
        List<Article> articles = articleMapper.findAllArticles();
        PageInfo<Article> pageInfo = new PageInfo<>(articles);
        List<Map<String, Object>> newArticles = new ArrayList<>();
        Map<String, Object> map;

        for(Article article : articles){
            map = new HashMap<>();
            map.put("thisArticleUrl", "/article/" + article.getArticleId());
            map.put("articleTags", StringAndArray.stringToArray(article.getArticleTags()));
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
        return DataMap.success().setData(jsonArray);
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
    public DataMap updateLikeByArticleId(long articleId) {

        articleMapper.updateLikeByArticleId(articleId);
        int likes = articleMapper.findLikesByArticleId(articleId);
        return DataMap.success().setData(likes);
    }

    @Override
    public DataMap findArticleByTag(String tag, int rows, int pageNum) {

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
        jsonObject.put("result",articleJsonArray);
        jsonObject.put("tag",tag);
        jsonObject.put("pageInfo",pageJson);
        return DataMap.success(CodeType.FIND_ARTICLE_BY_TAG).setData(jsonObject);
    }

    @Override
    public DataMap findArticleByCategory(String category, int rows, int pageNum) {

        List<Article> articles;
        PageInfo<Article> pageInfo;
        JSONArray articleJsonArray = new JSONArray();
        PageHelper.startPage(pageNum, rows);
        if(StringUtil.BLANK.equals(category)){
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
        jsonObject.put("result",articleJsonArray);
        jsonObject.put("category",category);
        jsonObject.put("pageInfo",pageJson);

        return DataMap.success().setData(jsonObject);
    }

    @Override
    public DataMap findArticleByArchive(String archive, int rows, int pageNum) {
        List<Article> articles;
        PageInfo<Article> pageInfo;
        JSONArray articleJsonArray = new JSONArray();
        TimeUtil timeUtil = new TimeUtil();
        String showMonth = "hide";
        if(!StringUtil.BLANK.equals(archive)){
            archive = timeUtil.timeYearToWhippletree(archive);
        }
        PageHelper.startPage(pageNum, rows);
        if(StringUtil.BLANK.equals(archive)){
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
        jsonObject.put("result",articleJsonArray);
        jsonObject.put("pageInfo",pageJson);
        jsonObject.put("articleNum", articleMapper.countArticle());
        jsonObject.put("showMonth", showMonth);

        return DataMap.success().setData(jsonObject);
    }

    @Override
    public DataMap getDraftArticle(Article article, String[] articleTags, int articleGrade) {
        Map<String, Object> dataMap = new HashMap<>(16);
        dataMap.put("id", article.getId());
        dataMap.put("articleTitle", article.getArticleTitle());
        dataMap.put("articleType", article.getArticleType());
        dataMap.put("articleCategories", article.getArticleCategories());
        dataMap.put("articleUrl", article.getArticleUrl());
        dataMap.put("originalAuthor", article.getOriginalAuthor());
        dataMap.put("articleContent", article.getArticleContent());
        dataMap.put("articleTags", articleTags);
        dataMap.put("articleGrade", articleGrade);
        return DataMap.success().setData(dataMap);
    }

    @Override
    public DataMap getArticleManagement(int rows, int pageNum) {
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
        returnJson.put("result",returnJsonArray);
        JSONObject pageJson = new JSONObject();
        pageJson.put("pageNum",pageInfo.getPageNum());
        pageJson.put("pageSize",pageInfo.getPageSize());
        pageJson.put("total",pageInfo.getTotal());
        pageJson.put("pages",pageInfo.getPages());
        pageJson.put("isFirstPage",pageInfo.isIsFirstPage());
        pageJson.put("isLastPage",pageInfo.isIsLastPage());

        returnJson.put("pageInfo",pageJson);

        return DataMap.success().setData(returnJson);
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
    public DataMap deleteArticle(long id) {
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
            log.error("Delete article exception,article id is [{}]", id, e);
            return DataMap.fail(CodeType.DELETE_ARTICLE_FAIL);
        }
        return DataMap.success();
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

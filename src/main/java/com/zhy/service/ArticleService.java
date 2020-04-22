package com.zhy.service;

import com.zhy.model.Article;
import com.zhy.utils.DataMap;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

/**
 * @author: zhangocean
 * @Date: 2018/6/20 21:41
 * Describe: 文章业务操作
 */
public interface ArticleService {

    /**
     * 保存文章
     * @param article 文章
     * @return  status: 200--成功   500--失败
     */

    DataMap insertArticle(Article article);

    /**
     * 修改文章
     * @return
     */
    @Transactional
    DataMap updateArticleById(Article article);

    /**
     * 获得文章
     * @param articleId 文章id
     * @return
     */
    DataMap getArticleByArticleId(long articleId, String username);

    /**
     * 通过文章id获得文章名和文章摘要
     * @param id 文章id
     * @return 文章名
     */
    Map<String, String> findArticleTitleByArticleId(long id);

    /**
     * 分页获得所有文章
     * @param rows 一页显示文章数
     * @param pageNo 第几页
     * @return 该页所有文章
     */
    DataMap findAllArticles(int rows, int pageNum);

    /**
     * 通过文章id更新它的上一篇或下一篇文章id
     * @param lastOrNext
     * @param lastOrNextArticleId
     * @param articleId
     */
    void updateArticleLastOrNextId(String lastOrNext, long lastOrNextArticleId, long articleId);

    /**
     * 文章点赞
     * @param articleId 文章id
     * @return 目前点赞数
     */
    DataMap updateLikeByArticleId(long articleId);

    /**
     * 通过标签分页获得文章部分信息
     * @param tag
     * @return
     */
    DataMap findArticleByTag(String tag, int rows, int pageNum);

    /**
     * 分页获得该分类下的所有文章
     * @param category 分类名
     * @param rows 一页大小
     * @param pageNum 页数
     * @return
     */
    DataMap findArticleByCategory(String category, int rows, int pageNum);

    /**
     * 分页获得该归档日期下的所有文章
     * @param archive 归档日期
     * @param rows 一页大小
     * @param pageNum 页数
     * @return
     */
    DataMap findArticleByArchive(String archive, int rows, int pageNum);

    /**
     * 获得草稿中的文章
     * @return
     */
    DataMap getDraftArticle(Article article, String[] articleTags, int articleGrade);

    /**
     * 分页获得文章管理
     */
    DataMap getArticleManagement(int rows, int pageNum);

    /**
     * 通过id获取文章
     */
    Article findArticleById(int id);

    /**
     * 计算该分类文章的数目
     * @param category 分类名
     * @return 该分类下文章的数目
     */
    int countArticleCategoryByCategory(String category);

    /**
     * 计算该归档日期文章的数目
     * @param archive 归档日期
     * @return 该归档日期下文章的数目
     */
    int countArticleArchiveByArchive(String archive);

    /**
     * 计算所有文章的数量
     * @return 所有文章的数量
     */
    int countArticle();

    /**
     * 通过id删除文章
     * @param id 文章id
     */
    @Transactional
    DataMap deleteArticle(long id);

}

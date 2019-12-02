package com.zhy.mapper;

import com.zhy.model.Article;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author: zhangocean
 * @Date: 2018/6/20 21:36
 * Describe: 文章sql
 */
@Mapper
@Repository
public interface ArticleMapper {

    @Insert("insert into article(articleId,author,originalAuthor,articleTitle,publishDate,updateDate,articleContent,articleTags,articleType,articleCategories,articleUrl,articleTabloid,likes,lastArticleId,nextArticleId) " +
            "values(#{articleId},#{author},#{originalAuthor},#{articleTitle},#{publishDate},#{updateDate},#{articleContent},#{articleTags},#{articleType},#{articleCategories},#{articleUrl},#{articleTabloid},#{likes},#{lastArticleId},#{nextArticleId})")
    void save(Article article);

    @Update("update article set originalAuthor=#{originalAuthor},articleTitle=#{articleTitle},updateDate=#{updateDate},articleContent=#{articleContent},articleTags=#{articleTags},articleType=#{articleType},articleCategories=#{articleCategories},articleUrl=#{articleUrl},articleTabloid=#{articleTabloid} where id=#{id}")
    void updateArticleById(Article article);

    @Select("select articleId from article where id=#{id}")
    Article getArticleUrlById(int id);

    @Select("select * from article where articleId=#{articleId}")
    Article getArticleByArticleId(@Param("articleId") long articleId);

    @Select("select articleTitle,articleTabloid from article where articleId=#{articleId}")
    Article findArticleTitleByArticleId(@Param("articleId") long articleId);

    @Select("select articleId,articleTitle from article where articleId=#{articleId}")
    Article findArticleByArticleId(@Param("articleId") long articleId);

    @Select("select articleId,originalAuthor,articleTags,articleTitle,articleType,publishDate,originalAuthor,articleCategories,articleTabloid,likes from article order by id desc")
    List<Article> findAllArticles();

    @Select("select articleId from article order by id desc limit 1")
    Article findEndArticleId();

    @Select("update article set lastArticleId=#{lastArticleId} where articleId=#{articleId}")
    void updateArticleLastId(@Param("lastArticleLd") long lastArticleLd, @Param("articleId") long articleId);

    @Select("update article set nextArticleId=#{nextArticleId} where articleId=#{articleId}")
    void updateArticleNextId(@Param("nextArticleId") long nextArticleId, @Param("articleId") long articleId);

    @Update("update article set likes=likes+1 where articleId=#{articleId}")
    void updateLikeByArticleId(@Param("articleId") long articleId);

    @Select("select IFNULL(max(likes),0) from article where articleId=#{articleId}")
    int findLikesByArticleId(@Param("articleId") long articleId);

    @Select("select articleId,originalAuthor,articleTitle,articleTags,articleType,articleCategories,publishDate from article where articleTags like '%${tag}%' order by id desc")
    List<Article> findArticleByTag(@Param("tag") String tag);

    @Select("select articleId,originalAuthor,articleTitle,articleTags,articleType,articleCategories,publishDate from article where articleCategories=#{category} order by id desc")
    List<Article> findArticleByCategory(@Param("category") String category);

    @Select("select articleId,originalAuthor,articleTitle,articleTags,articleType,articleCategories,publishDate from article where publishDate like '%${archive}%' order by id desc")
    List<Article> findArticleByArchive(@Param("archive") String archive);

    @Select("select articleId,originalAuthor,articleTitle,articleTags,articleType,articleCategories,publishDate from article order by id desc")
    List<Article> findAllArticlesPartInfo();

    @Select("select id,articleId,originalAuthor,articleTitle,articleCategories,publishDate from article order by id desc")
    List<Article> getArticleManagement();

    @Select("select id,articleId,originalAuthor,articleTitle,articleContent,articleCategories,articleTags,articleType,articleUrl from article where id=#{id}")
    Article findArticleById(int id);

    @Select("select count(*) from article where articleCategories=#{category}")
    int countArticleCategoryByCategory(@Param("category") String category);

    @Select("select count(*) from article where publishDate like '%${archive}%'")
    int countArticleArchiveByArchive(@Param("archive") String archive);

    @Select("select count(*) from article")
    int countArticle();

    @Select("select articleId, lastArticleId, nextArticleId from article where id=#{id}")
    Article findAllArticleId(long id);

    @Update("update article set ${lastOrNextStr}=#{updateId} where articleId=#{articleId}")
    void updateLastOrNextId(@Param("lastOrNextStr") String lastOrNextStr, @Param("updateId") long updateId, @Param("articleId") long articleId);

    @Delete("delete from article where articleId=#{articleId}")
    void deleteByArticleId(long articleId);
}

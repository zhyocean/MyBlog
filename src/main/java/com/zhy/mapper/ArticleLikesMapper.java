package com.zhy.mapper;

import com.zhy.model.ArticleLikesRecord;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author: zhangocean
 * @Date: 2018/7/7 15:51
 * Describe: 文章点赞sql
 */
@Mapper
@Repository
public interface ArticleLikesMapper {

    @Insert("insert into article_likes_record(articleId,likerId,likeDate,isRead) values(#{articleId},#{likerId},#{likeDate},#{isRead})")
    void save(ArticleLikesRecord articleLikesRecord);

    @Select("select likeDate from article_likes_record where articleId=#{articleId} and likerId=#{likerId}")
    ArticleLikesRecord isLiked(@Param("articleId") long articleId, @Param("likerId") int likerId);

    @Delete("delete from article_likes_record where articleId=#{articleId}")
    void deleteArticleLikesRecordByArticleId(long articleId);

    @Select("select * from article_likes_record order by id desc")
    List<ArticleLikesRecord> getArticleThumbsUp();

    @Select("select count(*) from article_likes_record where isRead=1")
    int countIsReadNum();

    @Update("update article_likes_record set isRead=0 where id=#{id}")
    void readThisThumbsUp(int id);

    @Update("update article_likes_record set isRead=0")
    void readAllThumbsUp();
}

package com.zhy.mapper;

import com.zhy.model.Comment;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author: zhangocean
 * @Date: 2018/7/6 16:47
 * Describe: 评论sql
 */
@Repository
@Mapper
public interface CommentMapper {

    @Insert("insert into comment_record(articleId,pId,answererId,respondentId,commentDate,likes,commentContent)" +
            " values(#{articleId},#{pId},#{answererId},#{respondentId},#{commentDate},#{likes},#{commentContent})")
    void insertComment(Comment comment);

    @Select("select * from comment_record where articleId=#{articleId} and pId=#{pId} order by id desc")
    List<Comment> findCommentByArticleIdAndPid(@Param("articleId") long articleId, @Param("pId") long pId);

    @Select("select * from comment_record where articleId=#{articleId} and pId=#{pId}")
    List<Comment> findCommentByArticleIdAndPidNoOrder(@Param("articleId") long articleId, @Param("pId") long pId);

    @Update("update comment_record set likes=likes+1 where articleId=#{articleId} and id=#{id}")
    void updateLikeByArticleIdAndId(@Param("articleId") long articleId, @Param("id") long id);

    @Select("select IFNULL(max(likes),0) from comment_record where articleId=#{articleId} and id=#{id}")
    int findLikesByArticleIdAndId(@Param("articleId") long articleId, @Param("id") long id);

    @Select("select articleId,pId,answererId,respondentId,commentDate,commentContent from comment_record order by id desc")
    List<Comment> findFiveNewComment();

    @Select("select id,pId,articleId,answererId,respondentId,commentDate,commentContent from comment_record where answererId=#{answererId} order by id desc")
    List<Comment> getUserComment(@Param("answererId") int answererId);

    @Select("select count(*) from comment_record where pId=#{id}")
    int countReplyNumById(@Param("id") long id);

    @Select("select count(*) from comment_record where id>#{id} and pId=#{pId} and respondentId=#{respondentId}")
    int countReplyNumByIdAndRespondentId(@Param("pId") long pId, @Param("respondentId") int respondentId, @Param("id") long id);

    @Select("select count(*) from comment_record")
    int commentNum();

    @Delete("delete from comment_record where articleId=#{articleId}")
    void deleteCommentByArticleId(long articleId);
}

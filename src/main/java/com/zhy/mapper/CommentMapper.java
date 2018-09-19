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

    @Insert("insert into comment_record(articleId,originalAuthor,pId,answererId,respondentId,commentDate,likes,commentContent)" +
            " values(#{articleId},#{originalAuthor},#{pId},#{answererId},#{respondentId},#{commentDate},#{likes},#{commentContent})")
    void insertComment(Comment comment);

    @Select("select * from comment_record where articleId=#{articleId} and originalAuthor=#{originalAuthor} and pId=#{pId} order by id desc")
    List<Comment> findCommentByArticleIdAndOriginalAuthorAndPid(@Param("articleId") long articleId, @Param("originalAuthor") String originalAuthor, @Param("pId") long pId);

    @Select("select * from comment_record where articleId=#{articleId} and originalAuthor=#{originalAuthor} and pId=#{pId}")
    List<Comment> findCommentByArticleIdAndOriginalAuthorAndPidNoOrder(@Param("articleId") long articleId, @Param("originalAuthor") String originalAuthor, @Param("pId") long pId);

    @Update("update comment_record set likes=likes+1 where articleId=#{articleId} and originalAuthor=#{originalAuthor} and id=#{id}")
    void updateLikeByArticleIdAndOriginalAuthorAndId(@Param("articleId") long articleId, @Param("originalAuthor") String originalAuthor, @Param("id") long id);

    @Select("select IFNULL(max(likes),0) from comment_record where articleId=#{articleId} and originalAuthor=#{originalAuthor} and id=#{id}")
    int findLikesByArticleIdAndOriginalAuthorAndId(@Param("articleId") long articleId, @Param("originalAuthor") String originalAuthor, @Param("id") long id);

    @Select("select articleId,originalAuthor,pId,answererId,respondentId,commentDate,commentContent from comment_record order by id desc")
    List<Comment> findFiveNewComment();

    @Select("select id,pId,articleId,originalAuthor,answererId,respondentId,commentDate,commentContent from comment_record where answererId=#{answererId} order by id desc")
    List<Comment> getUserComment(@Param("answererId") int answererId);

    @Select("select count(*) from comment_record where pId=#{id}")
    int countReplyNumById(@Param("id") long id);

    @Select("select count(*) from comment_record where id>#{id} and pId=#{pId} and respondentId=#{respondentId}")
    int countReplyNumByIdAndRespondentId(@Param("pId") long pId, @Param("respondentId") int respondentId, @Param("id") long id);
}

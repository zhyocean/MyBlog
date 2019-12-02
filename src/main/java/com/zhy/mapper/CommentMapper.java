package com.zhy.mapper;

import com.zhy.model.Comment;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.StatementType;
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

    @SelectKey(statement="SELECT LAST_INSERT_ID()", keyProperty="id", before=false, statementType = StatementType.STATEMENT,resultType=int.class)
    @Insert("insert into comment_record(articleId,pId,answererId,respondentId,commentDate,likes,commentContent,isRead)" +
            " values(#{articleId},#{pId},#{answererId},#{respondentId},#{commentDate},#{likes},#{commentContent},#{isRead})")
    int save(Comment comment);

    @Select("select * from comment_record where articleId=#{articleId} and pId=#{pId} order by id desc")
    List<Comment> findCommentByArticleIdAndPid(@Param("articleId") long articleId, @Param("pId") long pId);

    @Select("select * from comment_record where articleId=#{articleId} and pId=#{pId}")
    List<Comment> findCommentByArticleIdAndPidNoOrder(@Param("articleId") long articleId, @Param("pId") long pId);

    @Update("update comment_record set likes=likes+1 where articleId=#{articleId} and id=#{id}")
    void updateLikeByArticleIdAndId(@Param("articleId") long articleId, @Param("id") long id);

    @Select("select IFNULL(max(likes),0) from comment_record where articleId=#{articleId} and id=#{id}")
    int findLikesByArticleIdAndId(@Param("articleId") long articleId, @Param("id") long id);

    @Select("select id,articleId,pId,answererId,respondentId,commentDate,commentContent from comment_record order by id desc")
    List<Comment> findFiveNewComment();

    @Select("select id,pId,articleId,answererId,commentDate,isRead from comment_record where respondentId=#{respondentId} and answererId<>#{respondentId} order by id desc")
    List<Comment> getUserCommentByRespondentId(@Param("respondentId") int respondentId);

    @Select("select count(*) from comment_record where isRead=1 and respondentId=#{respondentId} and answererId<>#{respondentId}")
    int countIsReadNumByRespondentId(@Param("respondentId") int respondentId);

    @Select("select count(*) from comment_record")
    int commentNum();

    @Delete("delete from comment_record where articleId=#{articleId}")
    void deleteCommentByArticleId(long articleId);

    @Update("update comment_record set isRead=0 where id=#{id}")
    void readCommentRecordById(int id);

    @Update("update comment_record set isRead=0 where respondentId=#{respondentId}")
    void readCommentRecordByRespondentId(int respondentId);
}

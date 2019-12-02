package com.zhy.mapper;

import com.zhy.model.LeaveMessage;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author: zhangocean
 * @Date: 2018/7/15 14:01
 * Describe: 留言sql
 */
@Mapper
@Repository
public interface LeaveMessageMapper {

    @Insert("insert into leave_message_record(pageName,pId,answererId,respondentId,leaveMessageDate,likes,leaveMessageContent,isRead) " +
            "values(#{pageName},#{pId},#{answererId},#{respondentId},#{leaveMessageDate},#{likes},#{leaveMessageContent},#{isRead})")
    void save(LeaveMessage leaveMessage);

    @Select("select * from leave_message_record where pageName=#{pageName} and pId=#{pId} order by id desc")
    List<LeaveMessage> findAllLeaveMessage(@Param("pageName") String pageName, @Param("pId") int pId);

    @Select("select id,answererId,respondentId,leaveMessageDate,leaveMessageContent from leave_message_record where pageName=#{pageName} and pId=#{pId}")
    List<LeaveMessage> findLeaveMessageReplyByPageNameAndPid(@Param("pageName") String pageName, @Param("pId") int pId);

    @Update("update leave_message_record set likes=likes+1 where pageName=#{pageName} and id=#{id}")
    void updateLikeByPageNameAndId(@Param("pageName") String pageName, @Param("id") int id);

    @Select("select IFNULL(max(likes),0) from leave_message_record where pageName=#{pageName} and id=#{id}")
    int findLikesByPageNameAndId(@Param("pageName") String pageName, @Param("id") int id);

    @Select("select id,pId,pageName,answererId,leaveMessageDate,isRead from leave_message_record where respondentId=#{respondentId} and answererId<>#{respondentId} order by id desc")
    List<LeaveMessage> getUserLeaveMessage(@Param("respondentId") int respondentId);

    @Select("select count(*) from leave_message_record where isRead=1 and respondentId=#{respondentId} and answererId<>#{respondentId}")
    int countIsReadNumByRespondentId(@Param("respondentId") int respondentId);

    @Select("select id,pageName,answererId,leaveMessageDate,leaveMessageContent from leave_message_record order by id desc")
    List<LeaveMessage> findFiveNewLeaveWord();

    @Select("select count(*) from leave_message_record")
    int countLeaveMessageNum();

    @Update("update leave_message_record set isRead=0 where id=#{id}")
    void readOneLeaveMessageRecord(int id);

    @Update("update leave_message_record set isRead=0 where respondentId=#{respondentId}")
    void readLeaveMessageRecordByRespondentId(int respondentId);
}

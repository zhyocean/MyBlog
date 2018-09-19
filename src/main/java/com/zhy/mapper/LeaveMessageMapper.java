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

    @Insert("insert into leave_message_record(pageName,pId,answererId,respondentId,leaveMessageDate,likes,leaveMessageContent) " +
            "values(#{pageName},#{pId},#{answererId},#{respondentId},#{leaveMessageDate},#{likes},#{leaveMessageContent})")
    void publishLeaveMessage(LeaveMessage leaveMessage);

    @Select("select * from leave_message_record where pageName=#{pageName} and pId=#{pId} order by id desc")
    List<LeaveMessage> findAllLeaveMessage(@Param("pageName") String pageName, @Param("pId") int pId);

    @Select("select answererId,respondentId,leaveMessageDate,leaveMessageContent from leave_message_record where pageName=#{pageName} and pId=#{pId}")
    List<LeaveMessage> findLeaveMessageReplyByPageNameAndPid(@Param("pageName") String pageName, @Param("pId") int pId);

    @Update("update leave_message_record set likes=likes+1 where pageName=#{pageName} and id=#{id}")
    void updateLikeByPageNameAndId(@Param("pageName") String pageName, @Param("id") int id);

    @Select("select IFNULL(max(likes),0) from leave_message_record where pageName=#{pageName} and id=#{id}")
    int findLikesByPageNameAndId(@Param("pageName") String pageName, @Param("id") int id);

    @Select("select pId,pageName,answererId,respondentId,leaveMessageDate,leaveMessageContent from leave_message_record where answererId=#{answererId} order by id desc")
    List<LeaveMessage> getUserLeaveMessage(@Param("answererId") int answererId);

    @Select("select count(*) from leave_message_record where pId=#{id}")
    int countReplyNumById(@Param("id") int id);

    @Select("select count(*) from leave_message_record where pId=#{id} and respondentId=#{respondentId}")
    int countReplyNumByIdAndRespondentId(@Param("id") int id, @Param("respondentId") int respondentId);

    @Select("select pageName,pId,answererId,respondentId,leaveMessageDate,leaveMessageContent from leave_message_record order by id desc")
    List<LeaveMessage> findFiveNewLeaveWord();
}

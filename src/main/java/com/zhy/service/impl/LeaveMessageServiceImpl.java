package com.zhy.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zhy.component.JavaScriptCheck;
import com.zhy.constant.SiteOwner;
import com.zhy.mapper.LeaveMessageMapper;
import com.zhy.model.LeaveMessage;
import com.zhy.model.UserReadNews;
import com.zhy.redis.HashRedisServiceImpl;
import com.zhy.service.LeaveMessageLikesRecordService;
import com.zhy.service.LeaveMessageService;
import com.zhy.service.UserService;
import com.zhy.utils.DataMap;
import com.zhy.utils.StringUtil;
import com.zhy.utils.TimeUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: zhangocean
 * @Date: 2018/7/15 14:01
 * Describe:
 */
@Service
public class LeaveMessageServiceImpl implements LeaveMessageService {

    @Autowired
    LeaveMessageMapper leaveMessageMapper;
    @Autowired
    LeaveMessageLikesRecordService leaveMessageLikesRecordService;
    @Autowired
    UserService userService;
    @Autowired
    HashRedisServiceImpl hashRedisServiceImpl;

    @Override
    public void publishLeaveMessage(String leaveMessageContent, String pageName, String answerer) {

        TimeUtil timeUtil = new TimeUtil();
        String nowStr = timeUtil.getFormatDateForFive();
        leaveMessageContent = JavaScriptCheck.javaScriptCheck(leaveMessageContent);
        LeaveMessage leaveMessage = new LeaveMessage(pageName, userService.findIdByUsername(answerer), userService.findIdByUsername(SiteOwner.SITE_OWNER), nowStr, leaveMessageContent);

        if(leaveMessage.getAnswererId() == leaveMessage.getRespondentId()){
            leaveMessage.setIsRead(0);
        }
        leaveMessageMapper.save(leaveMessage);
        //redis中保存该用户未读消息
        addNotReadNews(leaveMessage);
    }

    @Override
    public void publishLeaveMessageReply(LeaveMessage leaveMessage, String respondent) {
        TimeUtil timeUtil = new TimeUtil();
        String nowStr = timeUtil.getFormatDateForFive();
        leaveMessage.setLeaveMessageDate(nowStr);
        leaveMessage.setRespondentId(userService.findIdByUsername(respondent));
        if(leaveMessage.getAnswererId() == leaveMessage.getRespondentId()){
            leaveMessage.setIsRead(0);
        }
        leaveMessageMapper.save(leaveMessage);
        //redis中保存该用户未读消息
        addNotReadNews(leaveMessage);
    }

    @Override
    public DataMap leaveMessageNewReply(LeaveMessage leaveMessage, String answerer, String respondent) {
        Map<String, Object> dataMap = new HashMap<>(4);
        dataMap.put("answerer",answerer);
        dataMap.put("respondent",respondent);
        dataMap.put("leaveMessageContent",leaveMessage.getLeaveMessageContent());
        dataMap.put("leaveMessageDate",leaveMessage.getLeaveMessageDate());
        return DataMap.success().setData(dataMap);
    }

    @Override
    public DataMap findAllLeaveMessage(String pageName, int pId, String username) {

        List<LeaveMessage> leaveMessages = leaveMessageMapper.findAllLeaveMessage(pageName, pId);
        JSONObject returnJson,replyJson;
        JSONObject leaveMessageJson;
        JSONArray replyJsonArray;
        JSONArray leaveMessageJsonArray = new JSONArray();
        List<LeaveMessage> leaveMessageReplies;

        returnJson = new JSONObject();

        for(LeaveMessage leaveMessage : leaveMessages){
            leaveMessageJson = new JSONObject();
            leaveMessageJson.put("id",leaveMessage.getId());
            leaveMessageJson.put("answerer",userService.findUsernameById(leaveMessage.getAnswererId()));
            leaveMessageJson.put("leaveMessageDate",leaveMessage.getLeaveMessageDate());
            leaveMessageJson.put("likes",leaveMessage.getLikes());
            leaveMessageJson.put("avatarImgUrl",userService.getHeadPortraitUrlByUserId(leaveMessage.getAnswererId()));
            leaveMessageJson.put("leaveMessageContent",leaveMessage.getLeaveMessageContent());
            if(null == username){
                leaveMessageJson.put("isLiked",0);
            } else {
                if(!leaveMessageLikesRecordService.isLiked(pageName, leaveMessage.getId(), userService.findIdByUsername(username))){
                    leaveMessageJson.put("isLiked",0);
                } else {
                    leaveMessageJson.put("isLiked",1);
                }
            }

            leaveMessageReplies = leaveMessageMapper.findLeaveMessageReplyByPageNameAndPid(pageName, leaveMessage.getId());
            replyJsonArray = new JSONArray();
            for(LeaveMessage reply : leaveMessageReplies){
                replyJson = new JSONObject();
                replyJson.put("id", reply.getId());
                replyJson.put("answerer", userService.findUsernameById(reply.getAnswererId()));
                replyJson.put("respondent", userService.findUsernameById(reply.getRespondentId()));
                replyJson.put("leaveMessageDate", reply.getLeaveMessageDate());
                replyJson.put("leaveMessageContent", reply.getLeaveMessageContent());

                replyJsonArray.add(replyJson);
            }
            leaveMessageJson.put("replies",replyJsonArray);
            leaveMessageJsonArray.add(leaveMessageJson);
        }
        returnJson.put("result",leaveMessageJsonArray);

        return DataMap.success().setData(returnJson);
    }

    @Override
    public DataMap updateLikeByPageNameAndId(String pageName, int id) {
        leaveMessageMapper.updateLikeByPageNameAndId(pageName, id);
        int likes = leaveMessageMapper.findLikesByPageNameAndId(pageName, id);
        return DataMap.success().setData(likes);
    }

    @Override
    public DataMap getUserLeaveMessage(int rows, int pageNum, String username) {

        int respondentId = userService.findIdByUsername(username);
        PageHelper.startPage(pageNum, rows);
        List<LeaveMessage> leaveMessages = leaveMessageMapper.getUserLeaveMessage(respondentId);
        PageInfo<LeaveMessage> pageInfo = new PageInfo<>(leaveMessages);
        JSONObject returnJson = new JSONObject();
        JSONObject leaveMessageJson;
        JSONArray leaveMessageJsonArray = new JSONArray();
        for(LeaveMessage leaveMessage : leaveMessages){
            leaveMessageJson = new JSONObject();
            leaveMessageJson.put("id",leaveMessage.getId());
            leaveMessageJson.put("pId",leaveMessage.getPId());
            leaveMessageJson.put("pageName",leaveMessage.getPageName());
            leaveMessageJson.put("answerer",userService.findUsernameById(leaveMessage.getAnswererId()));
            leaveMessageJson.put("leaveMessageDate",leaveMessage.getLeaveMessageDate());
            leaveMessageJson.put("isRead",leaveMessage.getIsRead());
            leaveMessageJsonArray.add(leaveMessageJson);
        }

        returnJson.put("result",leaveMessageJsonArray);
        returnJson.put("msgIsNotReadNum",leaveMessageMapper.countIsReadNumByRespondentId(respondentId));

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
    public DataMap findFiveNewComment(int rows, int pageNum) {
        JSONObject returnJson = new JSONObject();
        PageHelper.startPage(pageNum, rows);
        List<LeaveMessage> fiveLeaveWords = leaveMessageMapper.findFiveNewLeaveWord();
        PageInfo<LeaveMessage> pageInfo = new PageInfo<>(fiveLeaveWords);

        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject;
        for(LeaveMessage leaveMessage : fiveLeaveWords){
            jsonObject = new JSONObject();
            if(leaveMessage.getPId() != 0){
                leaveMessage.setLeaveMessageContent("@" + userService.findUsernameById(leaveMessage.getRespondentId()) + " " + leaveMessage.getLeaveMessageContent());
            }
            jsonObject.put("id",leaveMessage.getId());
            jsonObject.put("pagePath",leaveMessage.getPageName());
            jsonObject.put("answerer",userService.findUsernameById(leaveMessage.getAnswererId()));
            jsonObject.put("leaveWordDate",leaveMessage.getLeaveMessageDate().substring(0,10));
            jsonObject.put("leaveWordContent",leaveMessage.getLeaveMessageContent());
            jsonArray.add(jsonObject);
        }

        returnJson.put("result",jsonArray);
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
    public int countLeaveMessageNum() {
        return leaveMessageMapper.countLeaveMessageNum();
    }

    @Override
    public void readOneLeaveMessageRecord(int id) {
        leaveMessageMapper.readOneLeaveMessageRecord(id);
    }

    @Override
    public void readAllLeaveMessage(String username) {
        int respondentId = userService.findIdByUsername(username);
        leaveMessageMapper.readLeaveMessageRecordByRespondentId(respondentId);
    }

    /**
     * 保存评论成功后往redis中增加一条未读评论数
     */
    private void addNotReadNews(LeaveMessage leaveMessage){
        if(leaveMessage.getRespondentId() != leaveMessage.getAnswererId()){
            Boolean isExistKey = hashRedisServiceImpl.hasKey(leaveMessage.getRespondentId()+ StringUtil.BLANK);
            if(!isExistKey){
                UserReadNews news = new UserReadNews(1,0,1);
                hashRedisServiceImpl.put(String.valueOf(leaveMessage.getRespondentId()), news, UserReadNews.class);
            } else {
                hashRedisServiceImpl.hashIncrement(leaveMessage.getRespondentId()+ StringUtil.BLANK, "allNewsNum",1);
                hashRedisServiceImpl.hashIncrement(leaveMessage.getRespondentId()+ StringUtil.BLANK, "leaveMessageNum",1);
            }
        }
    }
}

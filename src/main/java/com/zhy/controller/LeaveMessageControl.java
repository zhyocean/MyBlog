package com.zhy.controller;

import com.zhy.aspect.annotation.PermissionCheck;
import com.zhy.component.JavaScriptCheck;
import com.zhy.constant.CodeType;
import com.zhy.model.LeaveMessage;
import com.zhy.model.LeaveMessageLikesRecord;
import com.zhy.service.LeaveMessageLikesRecordService;
import com.zhy.service.LeaveMessageService;
import com.zhy.service.UserService;
import com.zhy.utils.DataMap;
import com.zhy.utils.JsonResult;
import com.zhy.utils.StringUtil;
import com.zhy.utils.TimeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

/**
 * @author: zhangocean
 * @Date: 2018/7/15 13:55
 * Describe:
 */
@RestController
@Slf4j
public class LeaveMessageControl {

    @Autowired
    LeaveMessageService leaveMessageService;
    @Autowired
    LeaveMessageLikesRecordService leaveMessageLikesRecordService;
    @Autowired
    UserService userService;

    /**
     * 发表留言
     * @param leaveMessageContent 留言内容
     * @param pageName 留言页
     * @param principal 当前用户
     * @return
     */
    @PostMapping(value = "/publishLeaveMessage", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @PermissionCheck(value = "ROLE_USER")
    public String publishLeaveMessage(@RequestParam("leaveMessageContent") String leaveMessageContent,
                                          @RequestParam("pageName") String pageName,
                                          @AuthenticationPrincipal Principal principal){
        String answerer = principal.getName();
        try {
            leaveMessageService.publishLeaveMessage(leaveMessageContent,pageName, answerer);
            DataMap data = leaveMessageService.findAllLeaveMessage(pageName, 0, answerer);
            return JsonResult.build(data).toJSON();
        } catch (Exception e){
            log.error("[{}] publish leaveword [{}] on pageName [{}] exception", answerer, leaveMessageContent, pageName, e);
        }
        return JsonResult.fail(CodeType.SERVER_EXCEPTION).toJSON();
    }

    /**
     * 获得当前页的留言
     * @param pageName 当前页
     * @return
     */
    @GetMapping(value = "/getPageLeaveMessage", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String getPageLeaveMessage(@RequestParam("pageName") String pageName,
                                          @AuthenticationPrincipal Principal principal){
        String username = null;
        try {
            if(principal != null){
                username = principal.getName();
            }
            DataMap data = leaveMessageService.findAllLeaveMessage(pageName, 0, username);
            return JsonResult.build(data).toJSON();
        } catch (Exception e){
            log.error("[{}] get page [{}] leavemessage exception", username, pageName, e);
        }
        return JsonResult.fail(CodeType.SERVER_EXCEPTION).toJSON();
    }

    /**
     * 发布留言中的评论
     * @return
     */
    @PostMapping(value = "/publishLeaveMessageReply", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @PermissionCheck(value = "ROLE_USER")
    public String publishLeaveMessageReply(LeaveMessage leaveMessage,
                                           @RequestParam("parentId") String parentId,
                                           @RequestParam("respondent") String respondent,
                                           @AuthenticationPrincipal Principal principal){
        String username = principal.getName();
        try {
            leaveMessage.setAnswererId(userService.findIdByUsername(username));
            leaveMessage.setPId(Integer.parseInt(parentId.substring(1)));
            leaveMessage.setLeaveMessageContent(JavaScriptCheck.javaScriptCheck(leaveMessage.getLeaveMessageContent()));
            String commentContent = leaveMessage.getLeaveMessageContent();
            if('@' == commentContent.charAt(0)){
                leaveMessage.setLeaveMessageContent(commentContent.substring(respondent.length() + 1).trim());
            } else {
                leaveMessage.setLeaveMessageContent(commentContent.trim());
            }

            if(StringUtil.BLANK.equals(leaveMessage.getLeaveMessageContent())){
                return JsonResult.fail(CodeType.COMMENT_BLANK).toJSON();
            }

            leaveMessageService.publishLeaveMessageReply(leaveMessage, respondent);

            DataMap data = leaveMessageService.leaveMessageNewReply(leaveMessage, username, respondent);
            return JsonResult.build(data).toJSON();
        } catch (Exception e){
            log.error("[{}] publish leaveMessage reply [{}] exception", username, leaveMessage, e);
        }
        return JsonResult.fail(CodeType.SERVER_EXCEPTION).toJSON();
    }

    /**
     * 点赞
     * @return 点赞数
     */
    @GetMapping(value = "/addLeaveMessageLike", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @PermissionCheck(value = "ROLE_USER")
    public String addLeaveMessageLike(@RequestParam("pageName") String pageName,
                                   @RequestParam("respondentId") String respondentId,
                                   @AuthenticationPrincipal Principal principal){
        String username = principal.getName();
        try {
            TimeUtil timeUtil = new TimeUtil();
            int userId = userService.findIdByUsername(username);
            LeaveMessageLikesRecord leaveMessageLikesRecord = new LeaveMessageLikesRecord(pageName, Integer.parseInt(respondentId.substring(1)), userId, timeUtil.getFormatDateForFive());

            if(leaveMessageLikesRecordService.isLiked(leaveMessageLikesRecord.getPageName(), leaveMessageLikesRecord.getPId(), userId)){
                return JsonResult.fail(CodeType.MESSAGE_HAS_THUMBS_UP).toJSON();
            }
            DataMap data = leaveMessageService.updateLikeByPageNameAndId(pageName, leaveMessageLikesRecord.getPId());
            leaveMessageLikesRecordService.insertLeaveMessageLikesRecord(leaveMessageLikesRecord);
            return JsonResult.build(data).toJSON();
        } catch (Exception e){
            log.error("[{}] like page [{}] leaveMessage [{}] exception", username, pageName, respondentId, e);
        }
        return JsonResult.fail(CodeType.SERVER_EXCEPTION).toJSON();
    }

}

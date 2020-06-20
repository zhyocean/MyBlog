package com.zhy.controller;

import com.zhy.aspect.annotation.PermissionCheck;
import com.zhy.component.JavaScriptCheck;
import com.zhy.constant.CodeType;
import com.zhy.constant.SiteOwner;
import com.zhy.model.Comment;
import com.zhy.model.CommentLikesRecord;
import com.zhy.service.CommentLikesRecordService;
import com.zhy.service.CommentService;
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
 * @Date: 2018/7/5 23:14
 * Describe: 评论和回复
 */
@RestController
@Slf4j
public class CommentControl {

    @Autowired
    private CommentService commentService;
    @Autowired
    CommentLikesRecordService commentLikesRecordService;
    @Autowired
    UserService userService;

    /**
     * 获得该文章所有评论
     * @param articleId 文章id
     * @return
     */
    @PostMapping(value = "/getAllComment", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String getAllComment(@RequestParam("articleId") Long articleId,
                                   @AuthenticationPrincipal Principal principal){

        String username = StringUtil.BLANK;
        try {
            if(principal != null){
                username = principal.getName();
            }
            DataMap data = commentService.findCommentByArticleId(articleId,username);
            return JsonResult.build(data).toJSON();
        } catch (Exception e){
            log.error("Username [{}] get article [{}] all comment exception", username, articleId, e);
        }
        return JsonResult.fail(CodeType.SERVER_EXCEPTION).toJSON();
    }

    /**
     * 评论
     * @param principal 当前用户
     */
    @PostMapping(value = "/publishComment", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @PermissionCheck(value = "ROLE_USER")
    public String publishComment(Comment comment,
                                 @AuthenticationPrincipal Principal principal){

        String publisher = principal.getName();
        try {
            TimeUtil timeUtil = new TimeUtil();
            comment.setCommentDate(timeUtil.getFormatDateForFive());
            int userId = userService.findIdByUsername(publisher);
            comment.setAnswererId(userId);
            comment.setRespondentId(userService.findIdByUsername(SiteOwner.SITE_OWNER));
            comment.setCommentContent(JavaScriptCheck.javaScriptCheck(comment.getCommentContent()));

            commentService.insertComment(comment);

            DataMap data = commentService.findCommentByArticleId(comment.getArticleId(),publisher);
            return JsonResult.build(data).toJSON();
        } catch (Exception e){
            log.error("[{}] publish comment [{}] exception", publisher, comment, e);
        }
        return JsonResult.fail(CodeType.SERVER_EXCEPTION).toJSON();
    }

    /**
     * 评论中的回复
     * @param principal 当前用户
     */
    @PostMapping(value = "/publishReply", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @PermissionCheck(value = "ROLE_USER")
    public String publishReply(Comment comment,
                               @RequestParam("parentId") String parentId,
                               @RequestParam("respondent") String respondent,
                               @AuthenticationPrincipal Principal principal){

        String username = principal.getName();

        try {
            comment.setPId(Long.parseLong(parentId.substring(1)));
            comment.setAnswererId(userService.findIdByUsername(username));
            comment.setRespondentId(userService.findIdByUsername(respondent));
            TimeUtil timeUtil = new TimeUtil();
            comment.setCommentDate(timeUtil.getFormatDateForFive());
            String commentContent = comment.getCommentContent();

            //去掉评论中的@who
            if('@' == commentContent.charAt(0)){
                comment.setCommentContent(commentContent.substring(respondent.length() + 1).trim());
            } else {
                comment.setCommentContent(commentContent.trim());
            }
            //判断用户输入内容是否为空字符串
            if(StringUtil.BLANK.equals(comment.getCommentContent())){
                return JsonResult.fail(CodeType.COMMENT_BLANK).toJSON();
            } else {
                //防止xss攻击
                comment.setCommentContent(JavaScriptCheck.javaScriptCheck(comment.getCommentContent()));
                commentService.insertComment(comment);
            }
            DataMap data = commentService.replyReplyReturn(comment, username, respondent);
            return JsonResult.build(data).toJSON();
        } catch (Exception e){
            log.error("[{}] publish reply [{}] exception", username, comment, e);
        }
        return JsonResult.fail(CodeType.SERVER_EXCEPTION).toJSON();
    }

    /**
     * 是否登陆
     */
    @GetMapping(value = "/isLogin", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @PermissionCheck(value = "ROLE_USER")
    public String isLogin(){
        return JsonResult.success().toJSON();
    }

    /**
     * 评论点赞
     * @param articleId 文章id
     * @param respondentId 评论的id
     * @param principal 当前用户
     * @return 点赞数
     */
    @GetMapping(value = "/addCommentLike", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @PermissionCheck(value = "ROLE_USER")
    public String addCommentLike(@RequestParam("articleId") String articleId,
                              @RequestParam("respondentId") String respondentId,
                              @AuthenticationPrincipal Principal principal){

        String username = principal.getName();
        try {
            TimeUtil timeUtil = new TimeUtil();
            CommentLikesRecord commentLikesRecord = new CommentLikesRecord(Long.parseLong(articleId),
                    Integer.parseInt(respondentId.substring(1)),userService.findIdByUsername(username),timeUtil.getFormatDateForFive());
            if(commentLikesRecordService.isLiked(commentLikesRecord.getArticleId(), commentLikesRecord.getPId(), username)){
                return JsonResult.fail(CodeType.MESSAGE_HAS_THUMBS_UP).toJSON();
            }
            DataMap data = commentService.updateLikeByArticleIdAndId(commentLikesRecord.getArticleId(),commentLikesRecord.getPId());
            commentLikesRecordService.insertCommentLikesRecord(commentLikesRecord);
            return JsonResult.build(data).toJSON();
        } catch (Exception e){
            log.error("[{}] like article [{}] comment [{}] exception", username, articleId, respondentId, e);
        }
        return JsonResult.fail(CodeType.SERVER_EXCEPTION).toJSON();
    }

}

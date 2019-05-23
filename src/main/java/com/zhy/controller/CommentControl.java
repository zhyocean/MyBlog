package com.zhy.controller;

import com.zhy.component.JavaScriptCheck;
import com.zhy.constant.SiteOwner;
import com.zhy.model.Comment;
import com.zhy.model.CommentLikesRecord;
import com.zhy.service.CommentLikesRecordService;
import com.zhy.service.CommentService;
import com.zhy.service.UserService;
import com.zhy.utils.TimeUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;

/**
 * @author: zhangocean
 * @Date: 2018/7/5 23:14
 * Describe: 评论和回复
 */
@Controller
public class CommentControl {

    private Logger logger = LoggerFactory.getLogger(ShowArticleControl.class);

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
    @PostMapping("/getAllComment")
    @ResponseBody
    public JSONArray getAllComment(@RequestParam("articleId") Long articleId,
                                   @AuthenticationPrincipal Principal principal){

        String username = null;
        try {
            username = principal.getName();
        } catch (NullPointerException e){
        }
        return commentService.findCommentByArticleId(articleId,username);

    }

    /**
     * 评论
     * @param principal 当前用户
     * @return
     */
    @PostMapping("/publishComment")
    @ResponseBody
    public JSONArray publishComment(Comment comment,
                                    @AuthenticationPrincipal Principal principal){
        String publisher;
        try {
            publisher  = principal.getName();
        } catch (NullPointerException e){
            logger.error("no principal,please to login");
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("status",403);
            JSONArray jsonArray = new JSONArray();
            jsonArray.add(jsonObject);
            return jsonArray;
        }
        TimeUtil timeUtil = new TimeUtil();
        comment.setCommentDate(timeUtil.getFormatDateForFive());
        int userId = userService.findIdByUsername(publisher);
        comment.setAnswererId(userId);
        comment.setRespondentId(userService.findIdByUsername(SiteOwner.SITE_OWNER));
        comment.setCommentContent(JavaScriptCheck.javaScriptCheck(comment.getCommentContent()));

        commentService.insertComment(comment);

        JSONArray jsonArray = commentService.findCommentByArticleId(comment.getArticleId(),publisher);
        return jsonArray;
    }

    /**
     * 评论中的回复
     * @param principal 当前用户
     * @return
     */
    @PostMapping("/publishReply")
    @ResponseBody
    public JSONArray publishReply(Comment comment,
                                  @RequestParam("parentId") String parentId,
                                  @RequestParam("respondent") String respondent,
                                  @AuthenticationPrincipal Principal principal){

        String username = null;
        JSONArray jsonArray = new JSONArray();
        try {
            username = principal.getName();
        } catch (NullPointerException e){
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("status",403);
            jsonArray.add(jsonObject);
            return jsonArray;
        }

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
        if("".equals(comment.getCommentContent())){
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("status",400);
            jsonArray.add(jsonObject);
            return jsonArray;
        } else {
            //防止xss攻击
            comment.setCommentContent(JavaScriptCheck.javaScriptCheck(comment.getCommentContent()));
            comment = commentService.insertComment(comment);
        }
        jsonArray = commentService.replyReplyReturn(comment, username, respondent);
        return jsonArray;
    }

    /**
     * 是否登陆
     * @param principal 当前用户
     * @return
     */
    @GetMapping("/isLogin")
    @ResponseBody
    public int isLogin(@AuthenticationPrincipal Principal principal){
        String username;
        try {
            username = principal.getName();
            return 1;
        } catch (NullPointerException e){
            logger.info("This user is not login");
            return 0;
        }
    }

    /**
     * 评论点赞
     * @param articleId 文章id
     * @param respondentId 评论的id
     * @param principal 当前用户
     * @return 点赞数
     */
    @GetMapping("/addCommentLike")
    @ResponseBody
    public int addCommentLike(@RequestParam("articleId") String articleId,
                              @RequestParam("respondentId") String respondentId,
                              @AuthenticationPrincipal Principal principal){

        String username;
        try {
            username = principal.getName();
        } catch (NullPointerException e){
            logger.info("This user is not login");
            return -1;
        }

        TimeUtil timeUtil = new TimeUtil();
        CommentLikesRecord commentLikesRecord = new CommentLikesRecord(Long.parseLong(articleId),
                Integer.parseInt(respondentId.substring(1)),userService.findIdByUsername(username),timeUtil.getFormatDateForFive());
        if(commentLikesRecordService.isLiked(commentLikesRecord.getArticleId(), commentLikesRecord.getPId(), username)){
            logger.info("This user had clicked good for this article");
            return -2;
        }
        int likes = commentService.updateLikeByArticleIdAndId(commentLikesRecord.getArticleId(),commentLikesRecord.getPId());
        commentLikesRecordService.insertCommentLikesRecord(commentLikesRecord);
        return likes;
    }

}

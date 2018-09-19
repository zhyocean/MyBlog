package com.zhy.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zhy.mapper.CommentMapper;
import com.zhy.model.Comment;
import com.zhy.service.ArticleService;
import com.zhy.service.CommentLikesRecordService;
import com.zhy.service.CommentService;
import com.zhy.service.UserService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: zhangocean
 * @Date: 2018/7/6 16:47
 * Describe:
 */
@Service
public class CommentServiceImpl implements CommentService {

    private Logger logger = LoggerFactory.getLogger(CommentServiceImpl.class);

    @Autowired
    CommentMapper commentMapper;
    @Autowired
    CommentLikesRecordService commentLikesRecordService;
    @Autowired
    ArticleService articleService;
    @Autowired
    UserService userService;

    @Override
    public Comment insertComment(Comment comment, String respondent) {

        String commentContent = comment.getCommentContent();
        if('@' == commentContent.charAt(0)){
            comment.setCommentContent(commentContent.substring(respondent.length() + 1));
        }
        commentMapper.insertComment(comment);
        return comment;
    }

    @Override
    public JSONArray findCommentByArticleIdAndOriginalAuthor(long articleId, String originalAuthor, String username) {

        List<Comment> commentLists = commentMapper.findCommentByArticleIdAndOriginalAuthorAndPid(articleId, originalAuthor, 0);
        JSONArray commentJsonArray = new JSONArray();
        JSONArray replyJsonArray;
        JSONObject commentJsonObject;
        JSONObject replyJsonObject;
        List<Comment> replyLists;

        for(Comment comment : commentLists){
            commentJsonObject = new JSONObject();

            replyLists = commentMapper.findCommentByArticleIdAndOriginalAuthorAndPidNoOrder(articleId, originalAuthor, comment.getId());

            replyJsonArray = new JSONArray();
            //封装所有评论中的回复
            for(Comment reply : replyLists){
                replyJsonObject = new JSONObject();
                replyJsonObject.put("answerer",userService.findUsernameById(reply.getAnswererId()));
                replyJsonObject.put("commentDate",reply.getCommentDate());
                replyJsonObject.put("commentContent",reply.getCommentContent());
                replyJsonObject.put("respondent",userService.findUsernameById(reply.getRespondentId()));
                replyJsonArray.add(replyJsonObject);
            }

            //封装评论
            commentJsonObject.put("id",comment.getId());
            commentJsonObject.put("answerer",userService.findUsernameById(comment.getAnswererId()));
            commentJsonObject.put("commentDate",comment.getCommentDate());
            commentJsonObject.put("likes",comment.getLikes());
            commentJsonObject.put("commentContent",comment.getCommentContent());
            commentJsonObject.put("replies",replyJsonArray);
            commentJsonObject.put("avatarImgUrl",userService.getHeadPortraitUrlByUserId(comment.getAnswererId()));

            if(username == null){
                commentJsonObject.put("isLiked",0);
            } else {
                if(commentLikesRecordService.isLiked(articleId, originalAuthor, comment.getId(), username)){
                    commentJsonObject.put("isLiked",1);
                } else {
                    commentJsonObject.put("isLiked",0);
                }
            }
            commentJsonArray.add(commentJsonObject);
        }
        commentJsonObject = new JSONObject();
        commentJsonObject.put("status",200);
        commentJsonArray.add(commentJsonObject);
        return commentJsonArray;
    }

    @Override
    public JSONArray findReplyByArticleIdAndOriginalAuthorAndPid(long articleId, String originalAuthor, long pId) {
        List<Comment> commentList = commentMapper.findCommentByArticleIdAndOriginalAuthorAndPidNoOrder(articleId, originalAuthor, pId);
        JSONObject jsonObject;
        JSONArray jsonArray = new JSONArray();
        for(Comment comment : commentList){
            jsonObject = new JSONObject();
            jsonObject.put("answerer", userService.findUsernameById(comment.getAnswererId()));
            jsonObject.put("respondent", userService.findUsernameById(comment.getRespondentId()));
            jsonObject.put("commentContent", comment.getCommentContent());
            jsonObject.put("commentDate", comment.getCommentDate());
            jsonArray.add(jsonObject);
        }
        jsonObject = new JSONObject();
        jsonObject.put("status",200);
        jsonArray.add(jsonObject);
        logger.info("该评论下所有的回复是：" + jsonArray);
        return jsonArray;
    }

    @Override
    public JSONArray replyReplyReturn(Comment comment, String answerer, String respondent) {

        JSONObject jsonObject1 = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        jsonObject1.put("answerer", answerer);
        jsonObject1.put("respondent", respondent);
        jsonObject1.put("commentContent", comment.getCommentContent());
        jsonObject1.put("commentDate", comment.getCommentDate());

        JSONObject jsonObject2 = new JSONObject();
        jsonObject2.put("status",200);

        jsonArray.add(jsonObject1);
        jsonArray.add(jsonObject2);

        return jsonArray;
    }

    @Override
    public int updateLikeByArticleIdAndOriginalAuthorAndId(long articleId, String originalAuthor, long id) {
        commentMapper.updateLikeByArticleIdAndOriginalAuthorAndId(articleId, originalAuthor, id);
        return commentMapper.findLikesByArticleIdAndOriginalAuthorAndId(articleId, originalAuthor, id);
    }

    @Override
    public JSONObject findFiveNewComment(int rows, int pageNum) {

        JSONObject returnJson = new JSONObject();
        PageHelper.startPage(pageNum, rows);
        List<Comment> fiveComments = commentMapper.findFiveNewComment();
        PageInfo<Comment> pageInfo = new PageInfo<>(fiveComments);

        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject;

        for(Comment comment : fiveComments){
            jsonObject = new JSONObject();
            if(comment.getPId() != 0){
                comment.setCommentContent("@" + userService.findUsernameById(comment.getRespondentId()) + " " + comment.getCommentContent());
            }
            jsonObject.put("articleId",comment.getArticleId());
            jsonObject.put("originalAuthor",comment.getOriginalAuthor());
            jsonObject.put("answerer",userService.findUsernameById(comment.getAnswererId()));
            jsonObject.put("commentDate",comment.getCommentDate().substring(0,10));
            jsonObject.put("commentContent",comment.getCommentContent());
            jsonObject.put("articleTitle",articleService.findArticleTitleByArticleIdAndOriginalAuthor(comment.getArticleId(),comment.getOriginalAuthor()).get("articleTitle"));
            jsonArray.add(jsonObject);
        }
        returnJson.put("status",200);
        returnJson.put("result",jsonArray);
        JSONObject pageJson = new JSONObject();
        pageJson.put("pageNum",pageInfo.getPageNum());
        pageJson.put("pageSize",pageInfo.getPageSize());
        pageJson.put("total",pageInfo.getTotal());
        pageJson.put("pages",pageInfo.getPages());
        pageJson.put("isFirstPage",pageInfo.isIsFirstPage());
        pageJson.put("isLastPage",pageInfo.isIsLastPage());
        returnJson.put("pageInfo",pageJson);

        return returnJson;
    }

    @Override
    public JSONObject getUserComment(int rows, int pageNum, String username) {

        int userId = userService.findIdByUsername(username);
        PageHelper.startPage(pageNum, rows);
        List<Comment> comments = commentMapper.getUserComment(userId);
        PageInfo<Comment> pageInfo = new PageInfo<>(comments);

        JSONObject returnJson = new JSONObject();
        returnJson.put("status",200);
        JSONObject commentJson;
        JSONArray commentJsonArray = new JSONArray();
        for(Comment comment : comments){
            commentJson = new JSONObject();
            commentJson.put("articleId",comment.getArticleId());
            commentJson.put("originalAuthor",comment.getOriginalAuthor());
            commentJson.put("articleTitle",articleService.findArticleTitleByArticleIdAndOriginalAuthor(comment.getArticleId(), comment.getOriginalAuthor()).get("articleTitle"));
            commentJson.put("answerer", username);
            if(comment.getPId() == 0){
                commentJson.put("commentContent",comment.getCommentContent());
                commentJson.put("replyNum",commentMapper.countReplyNumById(comment.getId()));
            } else {
                commentJson.put("commentContent","@" + userService.findUsernameById(comment.getRespondentId()) + " " + comment.getCommentContent());
                commentJson.put("replyNum",commentMapper.countReplyNumByIdAndRespondentId(comment.getPId(), userId, comment.getId()));
            }
            commentJson.put("commentDate",comment.getCommentDate());
            commentJsonArray.add(commentJson);
        }
        returnJson.put("result",commentJsonArray);

        JSONObject pageJson = new JSONObject();
        pageJson.put("pageNum",pageInfo.getPageNum());
        pageJson.put("pageSize",pageInfo.getPageSize());
        pageJson.put("total",pageInfo.getTotal());
        pageJson.put("pages",pageInfo.getPages());
        pageJson.put("isFirstPage",pageInfo.isIsFirstPage());
        pageJson.put("isLastPage",pageInfo.isIsLastPage());

        returnJson.put("pageInfo",pageJson);

        return returnJson;
    }

}

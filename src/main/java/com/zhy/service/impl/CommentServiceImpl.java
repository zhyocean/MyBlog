package com.zhy.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zhy.mapper.CommentMapper;
import com.zhy.model.Comment;
import com.zhy.model.UserReadNews;
import com.zhy.redis.HashRedisServiceImpl;
import com.zhy.service.ArticleService;
import com.zhy.service.CommentLikesRecordService;
import com.zhy.service.CommentService;
import com.zhy.service.UserService;
import com.zhy.utils.DataMap;
import com.zhy.utils.StringUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
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

    @Autowired
    CommentMapper commentMapper;
    @Autowired
    CommentLikesRecordService commentLikesRecordService;
    @Autowired
    ArticleService articleService;
    @Autowired
    UserService userService;
    @Autowired
    HashRedisServiceImpl hashRedisServiceImpl;

    @Override
    public void insertComment(Comment comment) {
        if(comment.getAnswererId() == comment.getRespondentId()){
            comment.setIsRead(0);
        }
        commentMapper.save(comment);
        //redis中保存该用户未读消息
        addNotReadNews(comment);
    }

    @Override
    public DataMap findCommentByArticleId(long articleId, String username) {

        List<Comment> commentLists = commentMapper.findCommentByArticleIdAndPid(articleId, 0);
        JSONArray commentJsonArray = new JSONArray();
        JSONArray replyJsonArray;
        JSONObject commentJsonObject;
        JSONObject replyJsonObject;
        List<Comment> replyLists;

        for(Comment comment : commentLists){
            commentJsonObject = new JSONObject();

            replyLists = commentMapper.findCommentByArticleIdAndPidNoOrder(articleId, comment.getId());

            replyJsonArray = new JSONArray();
            //封装所有评论中的回复
            for(Comment reply : replyLists){
                replyJsonObject = new JSONObject();
                replyJsonObject.put("id",reply.getId());
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

            if(username.equals(StringUtil.BLANK)){
                commentJsonObject.put("isLiked",0);
            } else {
                if(commentLikesRecordService.isLiked(articleId, comment.getId(), username)){
                    commentJsonObject.put("isLiked",1);
                } else {
                    commentJsonObject.put("isLiked",0);
                }
            }
            commentJsonArray.add(commentJsonObject);
        }
        return DataMap.success().setData(commentJsonArray);
    }

    @Override
    public DataMap replyReplyReturn(Comment comment, String answerer, String respondent) {

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", comment.getId());
        jsonObject.put("answerer", answerer);
        jsonObject.put("respondent", respondent);
        jsonObject.put("commentContent", comment.getCommentContent());
        jsonObject.put("commentDate", comment.getCommentDate());

        return DataMap.success().setData(jsonObject);
    }

    @Override
    public DataMap updateLikeByArticleIdAndId(long articleId, long id) {
        commentMapper.updateLikeByArticleIdAndId(articleId, id);
        int liked = commentMapper.findLikesByArticleIdAndId(articleId, id);
        return DataMap.success().setData(liked);
    }

    @Override
    public DataMap findFiveNewComment(int rows, int pageNum) {

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
            jsonObject.put("id",comment.getId());
            jsonObject.put("articleId",comment.getArticleId());
            jsonObject.put("answerer",userService.findUsernameById(comment.getAnswererId()));
            jsonObject.put("commentDate",comment.getCommentDate().substring(0,10));
            jsonObject.put("commentContent",comment.getCommentContent());
            jsonObject.put("articleTitle",articleService.findArticleTitleByArticleId(comment.getArticleId()).get("articleTitle"));
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
    public DataMap getUserComment(int rows, int pageNum, String username) {

        int userId = userService.findIdByUsername(username);
        PageHelper.startPage(pageNum, rows);
        List<Comment> comments = commentMapper.getUserCommentByRespondentId(userId);
        PageInfo<Comment> pageInfo = new PageInfo<>(comments);

        JSONObject returnJson = new JSONObject();
        JSONObject commentJson;
        JSONArray commentJsonArray = new JSONArray();
        for(Comment comment : comments){
            commentJson = new JSONObject();
            commentJson.put("id",comment.getId());
            commentJson.put("pId",comment.getPId());
            commentJson.put("articleId",comment.getArticleId());
            commentJson.put("articleTitle",articleService.findArticleTitleByArticleId(comment.getArticleId()).get("articleTitle"));
            commentJson.put("answerer", userService.findUsernameById(comment.getAnswererId()));
            commentJson.put("commentDate",comment.getCommentDate());
            commentJson.put("isRead",comment.getIsRead());
            commentJsonArray.add(commentJson);
        }
        returnJson.put("result",commentJsonArray);
        returnJson.put("msgIsNotReadNum",commentMapper.countIsReadNumByRespondentId(userId));

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
    public int commentNum() {
        return commentMapper.commentNum();
    }

    @Override
    public void deleteCommentByArticleId(long articleId) {
        commentMapper.deleteCommentByArticleId(articleId);
    }

    @Override
    public void readOneCommentRecord(int id) {
        commentMapper.readCommentRecordById(id);
    }

    @Override
    public void readAllComment(String username) {
        int respondentId = userService.findIdByUsername(username);
        commentMapper.readCommentRecordByRespondentId(respondentId);
    }

    /**
     * 保存评论成功后往redis中增加一条未读评论数
     */
    private void addNotReadNews(Comment comment){
        if(comment.getRespondentId() != comment.getAnswererId()){
            boolean isExistKey = hashRedisServiceImpl.hasKey(comment.getRespondentId()+ StringUtil.BLANK);
            if(!isExistKey){
                UserReadNews news = new UserReadNews(1,1,0);
                hashRedisServiceImpl.put(String.valueOf(comment.getRespondentId()), news, UserReadNews.class);
            } else {
                hashRedisServiceImpl.hashIncrement(comment.getRespondentId()+ StringUtil.BLANK, "allNewsNum",1);
                hashRedisServiceImpl.hashIncrement(comment.getRespondentId()+ StringUtil.BLANK, "commentNum",1);
            }
        }
    }

}

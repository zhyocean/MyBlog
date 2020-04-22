package com.zhy.service;

import com.zhy.model.Comment;
import com.zhy.utils.DataMap;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author: zhangocean
 * @Date: 2018/7/6 16:46
 * Describe:评论业务操作
 */
public interface CommentService {

    /**
     * 保存留言
     * @param comment 留言
     */
    @Transactional
    void insertComment(Comment comment);

    /**
     * 通过文章id获得文章所有评论和回复
     * @param articleId 文章id
     * @return
     */
    DataMap findCommentByArticleId(long articleId, String username);

    /**
     * 返回评论中的回复
     * @param comment
     * @return
     */
    DataMap replyReplyReturn(Comment comment, String answerer, String respondent);

    /**
     * 更新评论点赞数
     * @param articleId 文章id
     * @param pId 该评论的id
     * @return 点赞数
     */
    DataMap updateLikeByArticleIdAndId(long articleId, long pId);

    /**
     * 获得最新的5条评论
     * @return
     */
    DataMap findFiveNewComment(int rows, int pageNum);

    /**
     * 分页获得用户所有的评论
     * @param rows 一页大小
     * @param pageNum 当前页
     * @param username 用户
     * @return
     */
    DataMap getUserComment(int rows, int pageNum, String username);

    /**
     * 获得评论总数
     */
    int commentNum();

    /**
     * 通过文章id删除该文章的所有评论
     * @param articleId 文章id'
     */
    void deleteCommentByArticleId(long articleId);

    /**
     * 已读一条评论
     * @param id 评论id
     */
    void readOneCommentRecord(int id);

    /**
     * 将该用户的所有未读消息标记为已读
     */
    void readAllComment(String username);

}

package com.zhy.service;

import com.zhy.model.CommentLikesRecord;
import org.springframework.stereotype.Service;

/**
 * @author: zhangocean
 * @Date: 2018/7/12 13:47
 * Describe:评论点赞记录业务操作
 */
@Service
public interface CommentLikesRecordService {

    /**
     * 评论是否点赞
     * @return true -- 已经点过赞  false -- 还没有点过赞
     */
    boolean isLiked(long articleId, String originalAuthor, long pId, String username);

    /**
     * 保存评论中点赞的记录
     * @param commentLikesRecord
     */
    void insertCommentLikesRecord(CommentLikesRecord commentLikesRecord);
}

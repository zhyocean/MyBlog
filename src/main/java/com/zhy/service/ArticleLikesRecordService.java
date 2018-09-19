package com.zhy.service;

import com.zhy.model.ArticleLikesRecord;
import org.springframework.stereotype.Service;

/**
 * @author: zhangocean
 * @Date: 2018/7/7 15:48
 * Describe: 文章点赞记录业务操作
 */
@Service
public interface ArticleLikesRecordService {

    /**
     * 文章是否已经点过赞
     * @param articledId 文章id
     * @param originalAuthor 原作者
     * @param username 点赞人
     * @return true--已经点过赞  false--没有点赞
     */
    boolean isLiked(long articleId, String originalAuthor, String username);

    /**
     * 保存文章中点赞的记录
     * @param articleLikesRecord
     */
    void insertArticleLikesRecord(ArticleLikesRecord articleLikesRecord);

}

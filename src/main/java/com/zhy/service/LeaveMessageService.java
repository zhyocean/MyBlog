package com.zhy.service;

import com.zhy.model.LeaveMessage;
import com.zhy.utils.DataMap;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author: zhangocean
 * @Date: 2018/7/15 14:01
 * Describe:留言业务操作
 */
public interface LeaveMessageService {

    /**
     * 保存留言信息
     * @param leaveMessageContent 留言内容
     * @param pageName 留言页
     * @param answerer 留言者
     */
    @Transactional
    void publishLeaveMessage(String leaveMessageContent, String pageName, String answerer);

    /**
     * 保存留言回复信息
     * @param leaveMessage
     */
    @Transactional
    void publishLeaveMessageReply(LeaveMessage leaveMessage, String respondent);

    /**
     * 返回最新的留言回复
     * @param leaveMessage
     * @return
     */
    DataMap leaveMessageNewReply(LeaveMessage leaveMessage, String answerer, String respondent);

    /**
     * 获得当前页的所有留言
     * @param pageName 当前页的名称
     * @param pId
     * @return
     */
    DataMap findAllLeaveMessage(String pageName, int pId, String username);

    /**
     * 更新点赞数
     * @return 点赞数
     */
    DataMap updateLikeByPageNameAndId(String pageName, int id);

    /**
     * 分页获得用户所有留言
     */
    DataMap getUserLeaveMessage(int rows, int pageNum, String username);

    /**
     * 返回最新5条留言
     */
    DataMap findFiveNewComment(int rows, int pageNum);

    /**
     * 获得留言总数
     */
    int countLeaveMessageNum();

    /**
     * 已读一条留言
     * @param id 评论id
     */
    void readOneLeaveMessageRecord(int id);

    /**
     * 全部标记为已读
     */
    void readAllLeaveMessage(String username);

}

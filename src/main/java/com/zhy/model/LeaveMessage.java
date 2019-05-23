package com.zhy.model;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: zhangocean
 * @Date: 2018/7/15 12:31
 * Describe: 留言
 */
@Data
@NoArgsConstructor
public class LeaveMessage {

    private int id;

    /**
     * 留言页
     */
    private String pageName;

    /**
     * 留言的父id 若是留言则为 0，则是留言中的回复则为对应留言的id
     */
    private int pId=0;

    /**
     * 留言者
     */
    private int answererId;

    /**
     * 被回复者
     */
    private int respondentId;

    /**
     * 留言日期
     */
    private String leaveMessageDate;

    /**
     * 喜欢数
     */
    private int likes=0;

    /**
     * 留言内容
     */
    private String leaveMessageContent;

    /**
     * 该条留言是否已读  1--未读   0--已读
     */
    private int isRead = 1;

    public LeaveMessage(String pageName, int answererId, int respondentId, String leaveMessageDate, String leaveMessageContent) {
        this.pageName = pageName;
        this.answererId = answererId;
        this.respondentId = respondentId;
        this.leaveMessageDate = leaveMessageDate;
        this.leaveMessageContent = leaveMessageContent;
    }
}

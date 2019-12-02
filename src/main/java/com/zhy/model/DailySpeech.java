package com.zhy.model;

import lombok.Data;

import java.util.Date;

/**
 * @author: zhangocean
 * @Date: 2018/11/28 15:01
 * Describe:
 */
@Data
public class DailySpeech {

    /**
     * 每天说的话
     */
    private String content;

    /**
     * 每天的心情
     */
    private String mood;

    /**
     * 图片url拼接后的字符串
     */
    private String picsUrl;

    /**
     * 发布日期
     */
    private Date publishDate;

}

package com.zhy.service;

import com.zhy.utils.DataMap;

/**
 * @author: zhangocean
 * @Date: 2018/7/16 19:50
 * Describe:标签业务操作
 */
public interface TagService {

    /**
     * 加入标签
     * @param tags 一群标签
     * @param tagSize 标签大小
     */
    void addTags(String[] tags, int tagSize);

    /**
     * 获得标签云
     * @return
     */
    DataMap findTagsCloud();

    /**
     * 获得标签云数量
     * @return
     */
    int countTagsNum();

    /**
     * 通过标签名获得标签大小
     * @param tagName
     * @return
     */
    int getTagsSizeByTagName(String tagName);
}

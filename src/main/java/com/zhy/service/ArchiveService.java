package com.zhy.service;

import com.zhy.utils.DataMap;

/**
 * @author: zhangocean
 * @Date: 2018/7/18 12:07
 * Describe: 归档业务操作
 */
public interface ArchiveService {

    /**
     * 获得归档信息
     * @return
     */
    DataMap findArchiveNameAndArticleNum();

    /**
     * 添加归档日期
     * @param archiveName
     */
    void addArchiveName(String archiveName);

}

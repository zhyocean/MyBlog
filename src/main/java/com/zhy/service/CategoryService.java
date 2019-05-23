package com.zhy.service;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;

/**
 * @author: zhangocean
 * @Date: 2018/7/17 20:52
 * Describe:分类业务操作
 */
public interface CategoryService {

    /**
     * 获得所有的分类以及该分类的文章总数
     * @return
     */
    JSONObject findCategoriesNameAndArticleNum();

    /**
     * 获得所有的分类
     * @return
     */
    JSONArray findCategoriesName();

    /**
     * 获得分类数目
     * @return
     */
    int countCategoriesNum();

    /**
     * 获得分类名和对应id
     */
    JSONObject findAllCategories();

    /**
     * 更新分类
     * @param categoryName 分类名
     * @param type 1--增加分类   2--删除分类
     */
    JSONObject updateCategory(String categoryName, int type);
}

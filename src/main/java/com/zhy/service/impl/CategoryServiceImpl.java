package com.zhy.service.impl;

import com.zhy.mapper.CategoryMapper;
import com.zhy.model.Categories;
import com.zhy.service.ArticleService;
import com.zhy.service.CategoryService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: zhangocean
 * @Date: 2018/7/17 20:54
 * Describe:
 */
@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    CategoryMapper categoryMapper;
    @Autowired
    ArticleService articleService;

    @Override
    public JSONObject findCategoriesNameAndArticleNum() {
        List<String> categoryNames = categoryMapper.findCategoriesName();
        JSONObject categoryJson;
        JSONArray categoryJsonArray = new JSONArray();
        JSONObject returnJson = new JSONObject();
        for(String categoryName : categoryNames){
            categoryJson = new JSONObject();
            categoryJson.put("categoryName",categoryName);
            categoryJson.put("categoryArticleNum",articleService.countArticleCategoryByCategory(categoryName));
            categoryJsonArray.add(categoryJson);
        }
        returnJson.put("status",200);
        returnJson.put("result",categoryJsonArray);
        return returnJson;
    }

    @Override
    public JSONArray findCategoriesName() {
        List<String> categoryNames = categoryMapper.findCategoriesName();
        return JSONArray.fromObject(categoryNames);
    }

    @Override
    public int countCategoriesNum() {
        return categoryMapper.countCategoriesNum();
    }

    @Override
    public JSONObject findAllCategories() {
        List<Categories> lists = categoryMapper.findAllCategories();
        JSONObject returnJson = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject;
        for(Categories c : lists){
            jsonObject = new JSONObject();
            jsonObject.put("id", c.getId());
            jsonObject.put("categoryName", c.getCategoryName());
            jsonArray.add(jsonObject);
        }

        returnJson.put("status", 200);
        returnJson.put("result", jsonArray);
        return returnJson;
    }

    @Override
    public JSONObject updateCategory(String categoryName, int type) {
        JSONObject jsonObject = new JSONObject();
        int isExistCategory = categoryMapper.findIsExistByCategoryName(categoryName);
        if(type == 1){
            if(isExistCategory == 0){
                Categories categories = new Categories();
                categories.setCategoryName(categoryName);
                categoryMapper.addCategory(categories);
                jsonObject.put("status", 200);
                jsonObject.put("id", categoryMapper.findIsExistByCategoryName(categoryName));
                jsonObject.put("msg", "添加分类成功！");
            } else {
                jsonObject.put("status", 201);
                jsonObject.put("msg", "分类已存在，请勿重复添加");
            }
        } else {
            if(isExistCategory != 0){
                categoryMapper.deleteCategory(categoryName);
                jsonObject.put("status", 202);
                jsonObject.put("msg", "删除分类成功！");
            }else {
                jsonObject.put("status", 203);
                jsonObject.put("msg", "分类不存在，删除失败！");
            }
        }
        return jsonObject;
    }

}

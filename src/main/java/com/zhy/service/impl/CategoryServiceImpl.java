package com.zhy.service.impl;

import com.zhy.constant.CodeType;
import com.zhy.mapper.CategoryMapper;
import com.zhy.model.Categories;
import com.zhy.service.ArticleService;
import com.zhy.service.CategoryService;
import com.zhy.utils.DataMap;
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
    public DataMap findCategoriesNameAndArticleNum() {
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
        returnJson.put("result",categoryJsonArray);
        return DataMap.success().setData(returnJson);
    }

    @Override
    public DataMap findCategoriesName() {
        List<String> categoryNames = categoryMapper.findCategoriesName();
        return DataMap.success().setData(categoryNames);
    }

    @Override
    public int countCategoriesNum() {
        return categoryMapper.countCategoriesNum();
    }

    @Override
    public DataMap findAllCategories() {
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

        returnJson.put("result", jsonArray);
        return DataMap.success().setData(returnJson);
    }

    @Override
    public DataMap updateCategory(String categoryName, int type) {
        int isExistCategory = categoryMapper.findIsExistByCategoryName(categoryName);
        if(type == 1){
            if(isExistCategory == 0){
                Categories categories = new Categories();
                categories.setCategoryName(categoryName);
                categoryMapper.save(categories);

                int id = categoryMapper.findIsExistByCategoryName(categoryName);

                return DataMap.success(CodeType.ADD_CATEGORY_SUCCESS)
                        .setData(id);
            } else {
                return DataMap.fail(CodeType.CATEGORY_EXIST);
            }
        } else {
            if(isExistCategory != 0){
                int articleNum = articleService.countArticleCategoryByCategory(categoryName);
                if(articleNum > 0){
                    return DataMap.fail(CodeType.CATEGORY_HAS_ARTICLE);
                }

                categoryMapper.deleteCategory(categoryName);
                return DataMap.success(CodeType.DELETE_CATEGORY_SUCCESS);
            }else {
                return DataMap.fail(CodeType.CATEGORY_NOT_EXIST);
            }
        }
    }

}

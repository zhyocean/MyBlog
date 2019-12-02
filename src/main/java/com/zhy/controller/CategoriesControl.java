package com.zhy.controller;

import com.zhy.service.ArticleService;
import com.zhy.service.CategoryService;
import com.zhy.utils.DataMap;
import com.zhy.utils.JsonResult;
import com.zhy.utils.StringUtil;
import com.zhy.utils.TransCodingUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author: zhangocean
 * @Date: 2018/7/17 20:50
 * Describe: 分类
 */
@RestController
public class CategoriesControl {

    @Autowired
    CategoryService categoryService;
    @Autowired
    ArticleService articleService;

    /**
     * 获得所有分类名以及每个分类名的文章数目
     * @return
     */
    @GetMapping(value = "/findCategoriesNameAndArticleNum", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String findCategoriesNameAndArticleNum(){
        DataMap data = categoryService.findCategoriesNameAndArticleNum();
        return JsonResult.build(data).toJSON();
    }

    /**
     * 分页获得该分类下的文章
     * @return
     */
    @GetMapping(value = "/getCategoryArticle", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String getCategoryArticle(@RequestParam("category") String category,
                                         HttpServletRequest request){
        if(!category.equals(StringUtil.BLANK)){
            category = TransCodingUtil.unicodeToString(category);
        }
        int rows = Integer.parseInt(request.getParameter("rows"));
        int pageNum = Integer.parseInt(request.getParameter("pageNum"));
        DataMap data = articleService.findArticleByCategory(category, rows, pageNum);
        return JsonResult.build(data).toJSON();
    }


}

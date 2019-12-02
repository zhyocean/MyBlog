package com.zhy.controller;

import com.zhy.service.ArticleService;
import com.zhy.utils.DataMap;
import com.zhy.utils.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: zhangocean
 * @Date: 2018/7/23 11:44
 * Describe:
 */
@RestController
public class MyStoryControl {

    @Autowired
    ArticleService articleService;

    @GetMapping(value = "/getMyStory", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String getMyStory(@RequestParam("rows") String rows,
                                 @RequestParam("pageNum") String pageNum){
        DataMap data = articleService.findArticleByCategory("我的故事",Integer.parseInt(rows), Integer.parseInt(pageNum));
        return JsonResult.build(data).toJSON();
    }

}

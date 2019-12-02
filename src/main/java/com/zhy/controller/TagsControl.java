package com.zhy.controller;

import com.zhy.service.ArticleService;
import com.zhy.service.TagService;
import com.zhy.utils.DataMap;
import com.zhy.utils.JsonResult;
import com.zhy.utils.StringUtil;
import com.zhy.utils.TransCodingUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author: zhangocean
 * @Date: 2018/7/16 21:27
 * Describe:
 */
@RestController
public class TagsControl {

    @Autowired
    TagService tagService;
    @Autowired
    ArticleService articleService;

    /**
     * 分页获得该标签下的文章
     * @param tag
     * @return
     */
    @PostMapping(value = "/getTagArticle", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String getTagArticle(@RequestParam("tag") String tag,
                                    HttpServletRequest request){
        if(tag.equals(StringUtil.BLANK)){
            return JsonResult.build(tagService.findTagsCloud()).toJSON();
        }

        tag = TransCodingUtil.unicodeToString(tag);
        int rows = Integer.parseInt(request.getParameter("rows"));
        int pageNum = Integer.parseInt(request.getParameter("pageNum"));
        DataMap data = articleService.findArticleByTag(tag, rows, pageNum);
        return JsonResult.build(data).toJSON();
    }

}

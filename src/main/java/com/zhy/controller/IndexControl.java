package com.zhy.controller;

import com.zhy.aspect.annotation.PermissionCheck;
import com.zhy.constant.CodeType;
import com.zhy.model.FeedBack;
import com.zhy.service.*;
import com.zhy.utils.DataMap;
import com.zhy.utils.JsonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: zhangocean
 * @Date: 2018/6/16 16:01
 * Describe:
 */
@RestController
@Slf4j
public class IndexControl {

    @Autowired
    VisitorService visitorService;
    @Autowired
    ArticleService articleService;
    @Autowired
    CommentService commentService;
    @Autowired
    TagService tagService;
    @Autowired
    CategoryService categoryService;
    @Autowired
    FeedBackService feedBackService;
    @Autowired
    UserService userService;
    @Autowired
    LeaveMessageService leaveMessageService;

    /**
     * 增加访客量
     * @return  网站总访问量以及访客量
     */
    @GetMapping(value = "/getVisitorNumByPageName", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String getVisitorNumByPageName(HttpServletRequest request,
                                          @RequestParam("pageName") String pageName){
        try {
            int index = pageName.indexOf("/");
            if(index == -1){
                pageName = "visitorVolume";
            }
            DataMap data = visitorService.addVisitorNumByPageName(pageName, request);
            return JsonResult.build(data).toJSON();
        } catch (Exception e){
            log.error("pageName [{}] get visitor num exception", pageName, e);
        }
        return JsonResult.fail(CodeType.SERVER_EXCEPTION).toJSON();
    }

    /**
     * 分页获得当前页文章
     * @param rows 一页的大小
     * @param pageNum 当前页
     */
    @PostMapping(value = "/myArticles", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String myArticles(@RequestParam("rows") String rows,
                             @RequestParam("pageNum") String pageNum){
        try {
            DataMap data = articleService.findAllArticles(rows, pageNum);
            return JsonResult.build(data).toJSON();
        } catch (Exception e){
            log.error("Homepage get paged article exception", e);
        }
        return JsonResult.fail(CodeType.SERVER_EXCEPTION).toJSON();
    }

    /**
     * 获得最新评论
     */
    @GetMapping(value = "/newComment", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String newComment(@RequestParam("rows") String rows,
                             @RequestParam("pageNum") String pageNum){
        DataMap data = commentService.findFiveNewComment(Integer.parseInt(rows),Integer.parseInt(pageNum));
        return JsonResult.build(data).toJSON();
    }

    /**
     * 获得最新留言
     */
    @GetMapping(value = "/newLeaveWord", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String newLeaveWord(@RequestParam("rows") String rows,
                               @RequestParam("pageNum") String pageNum){
        DataMap data = leaveMessageService.findFiveNewComment(Integer.parseInt(rows),Integer.parseInt(pageNum));
        return JsonResult.build(data).toJSON();
    }

    /**
     * 获得标签云
     */
    @GetMapping(value = "/findTagsCloud", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String findTagsCloud(){
        DataMap data = tagService.findTagsCloud();
        return JsonResult.build(data).toJSON();
    }

    /**
     * 获得右侧栏日志数、分类数、标签数
     */
    @GetMapping(value = "/findArchivesCategoriesTagsNum", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String findArchivesCategoriesTagsNum(){
        Map<String, Integer> dataMap = new HashMap<>(4);
        dataMap.put("tagsNum", tagService.countTagsNum());
        dataMap.put("categoriesNum", categoryService.countCategoriesNum());
        dataMap.put("archivesNum", articleService.countArticle());
        return JsonResult.success().data(dataMap).toJSON();
    }

    /**
     * 获得网站基本数据信息
     */
    @GetMapping(value = "/getSiteInfo", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String getSiteInfo(){
        Map<String, Integer> dataMap = new HashMap<>(4);
        dataMap.put("articleNum", articleService.countArticle());
        dataMap.put("tagsNum", tagService.countTagsNum());
        dataMap.put("leaveWordNum", leaveMessageService.countLeaveMessageNum());
        dataMap.put("commentNum", commentService.commentNum());
        return JsonResult.success().data(dataMap).toJSON();
    }

    /**
     * 反馈
     */
    @PostMapping(value = "/submitFeedback", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @PermissionCheck(value = "ROLE_USER")
    public String submitFeedback(FeedBack feedBack,
                                 @AuthenticationPrincipal Principal principal){
        String username = principal.getName();
        feedBack.setPersonId(userService.findIdByUsername(username));
        feedBackService.submitFeedback(feedBack);
        return JsonResult.success().toJSON();
    }
}

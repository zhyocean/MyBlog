package com.zhy.controller;

import com.zhy.model.FeedBack;
import com.zhy.service.*;
import com.zhy.utils.TransCodingUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.security.Principal;

/**
 * @author: zhangocean
 * @Date: 2018/6/16 16:01
 * Describe: 首页Controller
 */
@Controller
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
    @GetMapping("/getVisitorNumByPageName")
    public @ResponseBody JSONObject getVisitorNumByPageName(HttpServletRequest request,
                                                  @RequestParam("pageName") String pageName) throws UnsupportedEncodingException {

        int index = pageName.indexOf("?");
        if(index == -1){
            pageName = "visitorVolume";
        } else {
            String subPageName = pageName.substring(0, index);
            if("archives".equals(subPageName) || "categories".equals(subPageName) || "tags".equals(subPageName) || "login".equals(subPageName) || "register".equals(subPageName)){
                pageName = "visitorVolume";
            } else {
                //接收到文章的url将url中utf8的16进制数转换成汉字
                int originalAuthorIndex = pageName.indexOf("originalAuthor");
                String originalAuthorUtf18 = pageName.substring(originalAuthorIndex + 15);
                pageName = pageName.substring(0, originalAuthorIndex + 15) + TransCodingUtil.utf16ToUtf8(originalAuthorUtf18);
            }
        }
        visitorService.addVisitorNumByPageName(pageName, request);
        return visitorService.getVisitorNumByPageName(pageName);
    }

    /**
     * 分页获得当前页文章
     * @param rows 一页的大小
     * @param pageNum 当前页
     */
    @PostMapping("/myArticles")
    public @ResponseBody JSONArray myArticles(@RequestParam("rows") String rows,
                                @RequestParam("pageNum") String pageNum){

        return articleService.findAllArticles(rows, pageNum);

    }

    /**
     * 获得最新评论
     */
    @GetMapping("/newComment")
    @ResponseBody
    public JSONObject newComment(@RequestParam("rows") String rows,
                                @RequestParam("pageNum") String pageNum){

        return commentService.findFiveNewComment(Integer.parseInt(rows),Integer.parseInt(pageNum));
    }

    /**
     * 获得最新留言
     */
    @GetMapping("/newLeaveWord")
    @ResponseBody
    public JSONObject newLeaveWord(@RequestParam("rows") String rows,
                                   @RequestParam("pageNum") String pageNum){
        return leaveMessageService.findFiveNewComment(Integer.parseInt(rows),Integer.parseInt(pageNum));
    }

    /**
     * 获得标签云
     */
    @GetMapping("/findTagsCloud")
    @ResponseBody
    public JSONObject findTagsCloud(){
        return tagService.findTagsCloud();
    }

    /**
     * 获得右侧栏日志数、分类数、标签数
     */
    @GetMapping("/findArchivesCategoriesTagsNum")
    @ResponseBody
    public JSONObject findArchivesCategoriesTagsNum(){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("tagsNum", tagService.countTagsNum());
        jsonObject.put("categoriesNum", categoryService.countCategoriesNum());
        jsonObject.put("archivesNum", articleService.countArticle());
        return jsonObject;
    }

    /**
     * 反馈
     * @param feedBack
     * @param principal
     * @return
     */
    @PostMapping("/submitFeedback")
    @ResponseBody
    public JSONObject submitFeedback(FeedBack feedBack,
                                     @AuthenticationPrincipal Principal principal){
        String username;
        try {
            username = principal.getName();
        } catch (NullPointerException e){
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("status",403);
            return jsonObject;
        }
        feedBack.setPersonId(userService.findIdByUsername(username));
        return feedBackService.submitFeedback(feedBack);
    }
}

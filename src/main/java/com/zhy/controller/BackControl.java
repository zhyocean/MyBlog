package com.zhy.controller;

import com.zhy.service.ArticleService;
import com.zhy.utils.StringUtil;
import com.zhy.utils.TransCodingUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

/**
 * @author: zhangocean
 * @Date: 2018/5/17 19:24
 * Describe: 所有页面跳转
 */
@Controller
public class BackControl {

    private static final String SLASH_SYMBOL = "/";

    @Autowired
    ArticleService articleService;

    /**
     * 跳转首页
     */
    @GetMapping("/")
    public String index(HttpServletRequest request, HttpServletResponse response){
        response.setHeader("Access-Control-Allow-Origin", "*");
        //判断是否有需要回跳的url，有则将需要回跳的url保存在响应头中
        response.setHeader("lastUrl", (String) request.getSession().getAttribute("lastUrl"));
        return "index";
    }

    /**
     * 跳转我的女孩页
     */
    @GetMapping("/mylove")
    public String myLove(){
        return "mylove";
    }

    /**
     * 跳转我的藏心阁页
     */
    @GetMapping("/myheart")
    public String myheart(){
        return "myheart";
    }

    /**
     * 跳转我的故事页
     */
    @GetMapping("/mystory")
    public String mystory(HttpServletRequest request){
        request.getSession().removeAttribute("lastUrl");
        return "mystory";
    }

    /**
     * 跳转登录页
     */
    @GetMapping("/login")
    public String login(){
        return "login";
    }

    /**
     * 登录前尝试保存上一个页面的url
     */
    @GetMapping("/toLogin")
    @ResponseBody
    public void toLogin(HttpServletRequest request){
        //保存跳转页面的url
        String lastUrl = request.getHeader("Referer");
        if(lastUrl != null){
            try {
                URL url = new URL(lastUrl);
                if(!SLASH_SYMBOL.equals(url.getPath())){
                    request.getSession().setAttribute("lastUrl", lastUrl);
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 跳转注册页
     */
    @GetMapping("/register")
    public String register(){
        return "register";
    }

    /**
     * 跳转关于我页面
     */
    @GetMapping("/aboutme")
    public String aboutme(HttpServletRequest request){
        request.getSession().removeAttribute("lastUrl");
        return "aboutme";
    }

    /**
     * 跳转更新页
     */
    @GetMapping("/update")
    public String update(HttpServletRequest request){
        request.getSession().removeAttribute("lastUrl");
        return "update";
    }

    /**
     * 跳转友链页
     */
    @GetMapping("/friendlylink")
    public String friendlylink(HttpServletRequest request){
        request.getSession().removeAttribute("lastUrl");
        return "friendlylink";
    }

    /**
     * 跳转阿狸表白页
     */
    @GetMapping("/ali")
    public String ali(){
        return "ali";
    }

    /**
     * 跳转到用户页
     */
    @GetMapping("/user")
    public String user(HttpServletRequest request){
        request.getSession().removeAttribute("lastUrl");
        return "user";
    }

    /**
     * 跳转到文章编辑页
     */
    @GetMapping("/editor")
    public String editor(HttpServletRequest request){
        request.getSession().removeAttribute("lastUrl");
        String id = request.getParameter("id");
        if(!StringUtil.BLANK.equals(id)){
            request.getSession().setAttribute("id", id);
        }
        return "editor";
    }

    /**
     * 跳转到文章显示页
     */
    @GetMapping("/article/{articleId}")
    public String show(@PathVariable("articleId") long articleId,
                       HttpServletResponse response,
                       Model model,
                       HttpServletRequest request){
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        request.getSession().removeAttribute("lastUrl");

        Map<String, String> articleMap = articleService.findArticleTitleByArticleId(articleId);
        if(articleMap.get("articleTitle") != null){
            model.addAttribute("articleTitle",articleMap.get("articleTitle"));
            String articleTabloid = articleMap.get("articleTabloid");
            if(articleTabloid.length() <= 110){
                model.addAttribute("articleTabloid",articleTabloid);
            } else {
                model.addAttribute("articleTabloid",articleTabloid.substring(0,110));
            }
        }
        //将文章id存入响应头
        response.setHeader("articleId",String.valueOf(articleId));
        return "show";
    }

    /**
     * 跳转到归档页
     */
    @GetMapping("/archives")
    public String archives(HttpServletResponse response,
                           HttpServletRequest request){
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        request.getSession().removeAttribute("lastUrl");
        String archive = request.getParameter("archive");

        if(archive != null && !archive.equals(StringUtil.BLANK)){
            response.setHeader("archive",archive);
        }

        return "archives";
    }

    /**
     * 跳转到分类页
     */
    @GetMapping("/categories")
    public String categories(HttpServletResponse response,
                             HttpServletRequest request){
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        request.getSession().removeAttribute("lastUrl");
        String category = request.getParameter("category");

        if(category != null && !category.equals(StringUtil.BLANK)){
            response.setHeader("category", TransCodingUtil.stringToUnicode(category));
        }

        return "categories";
    }

    /**
     * 跳转到标签页
     */
    @GetMapping("/tags")
    public String tags(HttpServletResponse response,
                       HttpServletRequest request){
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        request.getSession().removeAttribute("lastUrl");
        String tag = request.getParameter("tag");

        if(tag != null && !tag.equals(StringUtil.BLANK)){
            response.setHeader("tag", TransCodingUtil.stringToUnicode(tag));
        }

        return "tags";
    }

    /**
     * 跳转到超级管理员页
     */
    @GetMapping("/superadmin")
    public String superadmin(HttpServletRequest request){
        request.getSession().removeAttribute("lastUrl");
        return "superadmin";
    }

//    @GetMapping("/yesterday")
//    public String yesterday(){
//        return "yesterday";
//    }

    @GetMapping("/today")
    public String today(){
        return "today";
    }

    @GetMapping("/reward")
    public String reward(HttpServletRequest request){
        request.getSession().removeAttribute("lastUrl");
        return "reward";
    }
}

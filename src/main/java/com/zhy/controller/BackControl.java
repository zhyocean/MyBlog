package com.zhy.controller;

import com.zhy.service.ArticleService;
import com.zhy.utils.TransCodingUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;
import java.util.Map;

/**
 * @author: zhangocean
 * @Date: 2018/5/17 19:24
 * Describe: 所有页面跳转
 */
@Controller
public class BackControl {

    @Autowired
    ArticleService articleService;


    @GetMapping("/")
    public String index(HttpServletRequest request, HttpServletResponse response,
                        @AuthenticationPrincipal Principal principal){
        String username = null;
        try {
            username = principal.getName();
        } catch (NullPointerException e){
            request.getSession().removeAttribute("lastUrl");
            return "index";
        }
        response.setHeader("lastUrl", (String) request.getSession().getAttribute("lastUrl"));
        return "index";
    }

    @GetMapping("/mylove")
    public String myLove(){
        return "mylove";
    }

    @GetMapping("/mystory")
    public String mystory(HttpServletRequest request){
        request.getSession().removeAttribute("lastUrl");
        return "mystory";
    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @GetMapping("/toLogin")
    public @ResponseBody void toLogin(HttpServletRequest request){
        //保存跳转页面的url
        request.getSession().setAttribute("lastUrl", request.getHeader("Referer"));
    }

    @GetMapping("/register")
    public String register(){
        return "register";
    }

    @GetMapping("/aboutme")
    public String aboutme(HttpServletRequest request){
        request.getSession().removeAttribute("lastUrl");
        return "aboutme";
    }

    @GetMapping("/update")
    public String update(HttpServletRequest request){
        request.getSession().removeAttribute("lastUrl");
        return "update";
    }

    @GetMapping("/friendlylink")
    public String friendlylink(HttpServletRequest request){
        request.getSession().removeAttribute("lastUrl");
        return "friendlylink";
    }

    @GetMapping("/ali")
    public String ali(){
        return "ali";
    }

    @GetMapping("/user")
    public String user(HttpServletRequest request){
        request.getSession().removeAttribute("lastUrl");
        return "user";
    }

    @GetMapping("/editor")
    public String editor(HttpServletRequest request){
        request.getSession().removeAttribute("lastUrl");
        String id = request.getParameter("id");
        if(!"".equals(id)){
            request.getSession().setAttribute("id", id);
        }
        return "editor";
    }

    @GetMapping("/findArticle")
    public String show(@RequestParam("articleId") String articleId,
                       @RequestParam("originalAuthor") String originalAuthor,
                       HttpServletResponse response,
                       Model model,
                       HttpServletRequest request){
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        request.getSession().removeAttribute("lastUrl");

        Map<String, String> articleMap = articleService.findArticleTitleByArticleIdAndOriginalAuthor(Long.parseLong(articleId), originalAuthor);
        model.addAttribute("articleTitle",articleMap.get("articleTitle"));
        String articleTabloid = articleMap.get("articleTabloid");
        if(articleTabloid.length() <= 110){
            model.addAttribute("articleTabloid",articleTabloid);
        } else {
            model.addAttribute("articleTabloid",articleTabloid.substring(0,110));
        }


        //将文章id和原作者存入响应头
        response.setHeader("articleId",articleId);
        response.setHeader("originalAuthor", TransCodingUtil.stringToUnicode(originalAuthor));
        return "show";
    }

    @GetMapping("/archives")
    public String archives(HttpServletResponse response,
                           HttpServletRequest request){
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        request.getSession().removeAttribute("lastUrl");
        String archive = request.getParameter("archive");

        try {
            response.setHeader("archive", TransCodingUtil.stringToUnicode(archive));
        } catch (Exception e){
        }
        return "archives";
    }

    @GetMapping("/categories")
    public String categories(HttpServletResponse response,
                             HttpServletRequest request){
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        request.getSession().removeAttribute("lastUrl");
        String category = request.getParameter("category");

        try {
            response.setHeader("category", TransCodingUtil.stringToUnicode(category));
        } catch (Exception e){
        }
        return "categories";
    }

    @GetMapping("/tags")
    public String tags(HttpServletResponse response,
                       HttpServletRequest request){
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        request.getSession().removeAttribute("lastUrl");

        String tag = request.getParameter("tag");
        try {
            response.setHeader("tag", TransCodingUtil.stringToUnicode(tag));
        } catch (Exception e){
        }
        return "tags";
    }

    @GetMapping("/superadmin")
    public String superadmin(HttpServletRequest request){
        request.getSession().removeAttribute("lastUrl");
        return "superadmin";
    }

}

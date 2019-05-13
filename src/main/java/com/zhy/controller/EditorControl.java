package com.zhy.controller;

import com.zhy.component.StringAndArray;
import com.zhy.model.Article;
import com.zhy.service.ArticleService;
import com.zhy.service.CategoryService;
import com.zhy.service.TagService;
import com.zhy.service.UserService;
import com.zhy.utils.BuildArticleTabloidUtil;
import com.zhy.utils.FileUtil;
import com.zhy.utils.TimeUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.security.Principal;
import java.sql.Time;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;


/**
 * @author: zhangocean
 * @Date: 2018/6/20 14:25
 * Describe:
 */
@Controller
public class EditorControl {

    private Logger logger = LoggerFactory.getLogger(EditorControl.class);

    @Autowired
    ArticleService articleService;
    @Autowired
    UserService userService;
    @Autowired
    TagService tagService;
    @Autowired
    CategoryService categoryService;

    /**
     * 发表博客
     * @param principal 当前登录用户
     * @param article 文章
     * @param request httpServletRequest
     * @return
     */
    @PostMapping("/publishArticle")
    @ResponseBody
    public JSONObject publishArticle(@AuthenticationPrincipal Principal principal,
                                     Article article,
                                     @RequestParam("articleGrade") String articleGrade,
                                     HttpServletRequest request){

        String username = null;
        JSONObject returnJson = new JSONObject();
        try {
            username = principal.getName();
        } catch (NullPointerException e){
            //登录超时情况
            logger.error("This user is not login，publish article 《" + article.getArticleTitle() +  "》 fail");
            returnJson.put("status",403);
            request.getSession().setAttribute("article", article);
            request.getSession().setAttribute("articleGrade", articleGrade);
            request.getSession().setAttribute("articleTags", request.getParameterValues("articleTagsValue"));
            return returnJson;
        }

        String phone = userService.findPhoneByUsername(username);
        if(!userService.isSuperAdmin(phone)){
            returnJson.put("status",500);
            return returnJson;
        }

        //获得文章html代码并生成摘要
        BuildArticleTabloidUtil buildArticleTabloidUtil = new BuildArticleTabloidUtil();
        String articleHtmlContent = buildArticleTabloidUtil.buildArticleTabloid(request.getParameter("articleHtmlContent"));
        article.setArticleTabloid(articleHtmlContent + "...");

        String[] articleTags = request.getParameterValues("articleTagsValue");
        String[] tags = new String[articleTags.length+1];
        for(int i=0;i<articleTags.length;i++){
            //去掉可能出现的换行符
            articleTags[i] = articleTags[i].replaceAll("<br>","").replaceAll("&nbsp;","").replaceAll("\\s+","").trim();
            tags[i] = articleTags[i];
        }
        tags[articleTags.length] = article.getArticleType();
        //添加标签
        tagService.addTags(tags, Integer.parseInt(articleGrade));
        TimeUtil timeUtil = new TimeUtil();
        String id = request.getParameter("id");
        //修改文章
        if(!"".equals(id) && id != null){
            String updateDate = timeUtil.getFormatDateForThree();
            article.setArticleTags(StringAndArray.arrayToString(tags));
            article.setUpdateDate(updateDate);
            article.setId(Integer.parseInt(id));
            article.setAuthor(username);
            return articleService.updateArticleById(article);
        }

        String nowDate = timeUtil.getFormatDateForThree();
        long articleId = timeUtil.getLongTime();

        article.setArticleId(articleId);
        article.setAuthor(username);
        article.setArticleTags(StringAndArray.arrayToString(tags));
        article.setPublishDate(nowDate);
        article.setUpdateDate(nowDate);

        returnJson = articleService.insertArticle(article);
        return returnJson;
    }

    /**
     * 验证是否有权限写博客
     * @param principal
     * @return
     */
    @GetMapping("/canYouWrite")
    @ResponseBody
    public int canYouWrite(@AuthenticationPrincipal Principal principal){

        String username = null;
        try {
            username = principal.getName();
        } catch (NullPointerException e){
            logger.info("This user is not login");
        }
        String phone = userService.findPhoneByUsername(username);
        if(userService.isSuperAdmin(phone)){
            return 1;
        }
        return 0;
    }

    /**
     * 获得所有的分类
     * @return
     */
    @GetMapping("/findCategoriesName")
    @ResponseBody
    public JSONArray findCategoriesName(){
        return categoryService.findCategoriesName();
    }

    /**
     * 获得是否有未发布的草稿文章或是修改文章
     */
    @GetMapping("/getDraftArticle")
    @ResponseBody
    public JSONObject getDraftArticle(HttpServletRequest request){
        JSONObject returnJson = new JSONObject();
        String id = (String) request.getSession().getAttribute("id");

        //判断是否为修改文章
        if(id != null){
            request.getSession().removeAttribute("id");
            returnJson.put("status",201);
            Article article = articleService.findArticleById(Integer.parseInt(id));
            int lastItem = article.getArticleTags().lastIndexOf(',');
            String[] articleTags = StringAndArray.stringToArray(article.getArticleTags().substring(0, lastItem));
            returnJson.put("result", articleService.getDraftArticle(article, articleTags, tagService.getTagsSizeByTagName(articleTags[0])));
            return returnJson;
        }
        //判断是否为写文章登录超时
        if(request.getSession().getAttribute("article") != null){
            returnJson.put("status",201);
            Article article = (Article) request.getSession().getAttribute("article");
            String[] articleTags = (String[]) request.getSession().getAttribute("articleTags");
            String articleGrade = (String) request.getSession().getAttribute("articleGrade");
            returnJson.put("result", articleService.getDraftArticle(article, articleTags, Integer.parseInt(articleGrade)));
            request.getSession().removeAttribute("article");
            request.getSession().removeAttribute("articleTags");
            request.getSession().removeAttribute("articleGrade");
            return returnJson;
        }
        returnJson.put("status",200);
        return returnJson;
    }

    /**
     * 文章编辑本地上传图片
     */
    @RequestMapping("/uploadImage")
    public @ResponseBody
    Map<String,Object> uploadImage(HttpServletRequest request, HttpServletResponse response,
                             @RequestParam(value = "editormd-image-file", required = false) MultipartFile file){
        Map<String,Object> resultMap = new HashMap<String,Object>();
        try {
            request.setCharacterEncoding( "utf-8" );
            //设置返回头后页面才能获取返回url
            response.setHeader("X-Frame-Options", "SAMEORIGIN");

            FileUtil fileUtil = new FileUtil();
            String filePath = this.getClass().getResource("/").getPath().substring(1) + "blogImg/";
            String fileContentType = file.getContentType();
            String fileExtension = fileContentType.substring(fileContentType.indexOf("/") + 1);
            TimeUtil timeUtil = new TimeUtil();
            String fileName = timeUtil.getLongTime() + "." + fileExtension;

            String subCatalog = "blogArticles/" + new TimeUtil().getFormatDateForThree();
            String fileUrl = fileUtil.uploadFile(fileUtil.multipartFileToFile(file, filePath, fileName), subCatalog);

            resultMap.put("success", 1);
            resultMap.put("message", "上传成功");
            resultMap.put("url", fileUrl);
        } catch (Exception e) {
            try {
                response.getWriter().write( "{\"success\":0}" );
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
        return resultMap;
    }
}

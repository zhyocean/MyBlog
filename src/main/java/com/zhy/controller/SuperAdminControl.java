package com.zhy.controller;

import com.zhy.aspect.annotation.PermissionCheck;
import com.zhy.constant.CodeType;
import com.zhy.model.FriendLink;
import com.zhy.model.Reward;
import com.zhy.redis.StringRedisServiceImpl;
import com.zhy.service.*;
import com.zhy.utils.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: zhangocean
 * @Date: 2018/7/25 16:14
 * Describe: 超级管理页面
 */
@RestController
@Slf4j
public class SuperAdminControl {

    @Autowired
    PrivateWordService privateWordService;
    @Autowired
    FeedBackService feedBackService;
    @Autowired
    VisitorService visitorService;
    @Autowired
    UserService userService;
    @Autowired
    ArticleService articleService;
    @Autowired
    ArticleLikesRecordService articleLikesRecordService;
    @Autowired
    StringRedisServiceImpl stringRedisService;
    @Autowired
    CategoryService categoryService;
    @Autowired
    FriendLinkService friendLinkService;
    @Autowired
    RedisService redisService;
    @Autowired
    private RewardService rewardService;

    /**
     * 获得所有悄悄话
     * @return
     */
    @PostMapping(value = "/getAllPrivateWord", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @PermissionCheck(value = "ROLE_SUPERADMIN")
    public String getAllPrivateWord(){
        try {
            DataMap data = privateWordService.getAllPrivateWord();
            return JsonResult.build(data).toJSON();
        } catch (Exception e){
            log.error("Get all private word exception", e);
        }
        return JsonResult.fail(CodeType.SERVER_EXCEPTION).toJSON();
    }

    /**
     * 回复悄悄话
     * @return
     */
    @PostMapping(value = "/replyPrivateWord", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @PermissionCheck(value = "ROLE_SUPERADMIN")
    public String replyPrivateWord(@AuthenticationPrincipal Principal principal,
                                       @RequestParam("replyContent") String replyContent,
                                       @RequestParam("replyId") String id){
        String username = principal.getName();
        try {
            DataMap data = privateWordService.replyPrivateWord(replyContent, username, Integer.parseInt(id));
            return JsonResult.build(data).toJSON();
        } catch (Exception e){
            log.error("[{}] Reply [{}] private word [{}] exception", username, id, replyContent, e);
        }
        return JsonResult.fail(CodeType.SERVER_EXCEPTION).toJSON();

    }

    /**
     * 分页获得所有反馈信息
     * @param rows 一页大小
     * @param pageNum 当前页
     */
    @GetMapping(value = "/getAllFeedback", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @PermissionCheck(value = "ROLE_SUPERADMIN")
    public String getAllFeedback(@RequestParam("rows") int rows,
                                     @RequestParam("pageNum") int pageNum){
        try {
            DataMap data = feedBackService.getAllFeedback(rows, pageNum);
            return JsonResult.build(data).toJSON();
        } catch (Exception e){
            log.error("Get all feedback exception", e);
        }
        return JsonResult.fail(CodeType.SERVER_EXCEPTION).toJSON();
    }

    /**
     * 获得统计信息
     * @return
     */
    @GetMapping(value = "/getStatisticsInfo", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @PermissionCheck(value = "ROLE_SUPERADMIN")
    public String getStatisticsInfo(){
        try {
            Map<String, Object> dataMap = new HashMap<>(8);
            Long totalVisitor = redisService.getVisitorNumOnRedis(StringUtil.VISITOR, "totalVisitor");
            Long yesterdayVisitor = redisService.getVisitorNumOnRedis(StringUtil.VISITOR, "yesterdayVisitor");
            dataMap.put("allVisitor", totalVisitor);
            dataMap.put("allUser", userService.countUserNum());
            dataMap.put("yesterdayVisitor", yesterdayVisitor);
            dataMap.put("articleNum", articleService.countArticle());
            if(stringRedisService.hasKey(StringUtil.ARTICLE_THUMBS_UP)){
                int articleThumbsUp = (int) stringRedisService.get(StringUtil.ARTICLE_THUMBS_UP);
                dataMap.put("articleThumbsUpNum", articleThumbsUp);
            } else {
                dataMap.put("articleThumbsUpNum", 0);
            }
            DataMap data = DataMap.success().setData(dataMap);
            return JsonResult.build(data).toJSON();
        } catch (Exception e){
            log.error("Get statistics info exception", e);
        }
        return JsonResult.fail(CodeType.SERVER_EXCEPTION).toJSON();
    }

    /**
     * 获得文章管理
     * @return
     */
    @PostMapping(value = "/getArticleManagement", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @PermissionCheck(value = "ROLE_SUPERADMIN")
    public String getArticleManagement(@RequestParam("rows") int rows,
                                           @RequestParam("pageNum") int pageNum){
        try {
            DataMap data = articleService.getArticleManagement(rows, pageNum);
            return JsonResult.build(data).toJSON();
        } catch (Exception e){
            log.error("Get article management exception", e);
        }
        return JsonResult.fail(CodeType.SERVER_EXCEPTION).toJSON();
    }

    /**
     * 删除文章
     * @param id 文章id
     */
    @GetMapping(value = "/deleteArticle", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @PermissionCheck(value = "ROLE_SUPERADMIN")
    public String deleteArticle(@RequestParam("id") String id){
        try {
            if(StringUtil.BLANK.equals(id) || id == null){
                return JsonResult.build(DataMap.fail(CodeType.DELETE_ARTICLE_FAIL)).toJSON();
            }
            DataMap data = articleService.deleteArticle(Long.parseLong(id));
            return JsonResult.build(data).toJSON();
        } catch (Exception e){
            log.error("Delete article [{}] exception", id, e);
        }
        return JsonResult.fail(CodeType.SERVER_EXCEPTION).toJSON();
    }

    /**
     * 获得文章点赞信息
     */
    @PostMapping(value = "/getArticleThumbsUp", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @PermissionCheck(value = "ROLE_SUPERADMIN")
    public String getArticleThumbsUp(@RequestParam("rows") int rows,
                                         @RequestParam("pageNum") int pageNum){
        try {
            DataMap data = articleLikesRecordService.getArticleThumbsUp(rows, pageNum);
            return JsonResult.build(data).toJSON();
        } catch (Exception e){
            log.error("Get article thumbsUp exception", e);
        }
        return JsonResult.fail(CodeType.SERVER_EXCEPTION).toJSON();
    }

    /**
     * 已读一条点赞信息
     */
    @GetMapping(value = "/readThisThumbsUp", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @PermissionCheck(value = "ROLE_SUPERADMIN")
    public String readThisThumbsUp(@RequestParam("id") int id){
        try {
            DataMap data = articleLikesRecordService.readThisThumbsUp(id);
            return JsonResult.build(data).toJSON();
        } catch (Exception e){
            log.error("Read one thumbsUp [{}] exception", id, e);
        }
        return JsonResult.fail(CodeType.SERVER_EXCEPTION).toJSON();
    }

    /**
     * 已读所有点赞信息
     */
    @GetMapping(value = "/readAllThumbsUp", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @PermissionCheck(value = "ROLE_SUPERADMIN")
    public String readAllThumbsUp(){
        try {
            DataMap data = articleLikesRecordService.readAllThumbsUp();
            return JsonResult.build(data).toJSON();
        } catch (Exception e){
            log.error("Read all thumbsUp exception", e);
        }
        return JsonResult.fail(CodeType.SERVER_EXCEPTION).toJSON();
    }

    /**
     * 获得所有分类
     */
    @GetMapping(value = "/getArticleCategories", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @PermissionCheck(value = "ROLE_SUPERADMIN")
    public String getArticleCategories(){
        try {
            DataMap data = categoryService.findAllCategories();
            return JsonResult.build(data).toJSON();
        } catch (Exception e){
            log.error("Get article categories exception", e);
        }
        return JsonResult.fail(CodeType.SERVER_EXCEPTION).toJSON();
    }

    /**
     * 添加或删除分类
     */
    @PostMapping(value = "/updateCategory", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @PermissionCheck(value = "ROLE_SUPERADMIN")
    public String updateCategory(@RequestParam("categoryName") String  categoryName,
                              @RequestParam("type") int type){
        try {
            DataMap data = categoryService.updateCategory(categoryName, type);
            return JsonResult.build(data).toJSON();
        } catch (Exception e){
            log.error("Update type [{}] article categories [{}] exception", type, categoryName, e);
        }
        return JsonResult.fail(CodeType.SERVER_EXCEPTION).toJSON();
    }

    /**
     * 获得友链
     */
    @PostMapping(value = "/getFriendLink", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @PermissionCheck(value = "ROLE_SUPERADMIN")
    public String getFriendLink(){
        try {
            DataMap data = friendLinkService.getAllFriendLink();
            return JsonResult.build(data).toJSON();
        } catch (Exception e){
            log.error("Get friendLink exception", e);
        }
        return JsonResult.fail(CodeType.SERVER_EXCEPTION).toJSON();
    }

    /**
     * 添加或编辑友链
     */
    @PostMapping(value = "/updateFriendLink", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @PermissionCheck(value = "ROLE_SUPERADMIN")
    public String addFriendLink(@RequestParam("id") String id,
                                @RequestParam("blogger") String blogger,
                                @RequestParam("url") String url){
        try {
            FriendLink friendLink = new FriendLink(blogger, url);
            DataMap data;
            if(StringUtil.BLANK.equals(id)){
                data = friendLinkService.addFriendLink(friendLink);
            } else {
                data = friendLinkService.updateFriendLink(friendLink, Integer.parseInt(id));
            }
            return JsonResult.build(data).toJSON();
        } catch (Exception e){
            log.error("Update friendLink [{}] exception", blogger, e);
        }
        return JsonResult.fail(CodeType.SERVER_EXCEPTION).toJSON();
    }

    /**
     * 删除友链
     */
    @PostMapping(value = "/deleteFriendLink", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @PermissionCheck(value = "ROLE_SUPERADMIN")
    public String deleteFriendLink(@RequestParam("id") int id){
        try {
            DataMap data = friendLinkService.deleteFriendLink(id);
            return JsonResult.build(data).toJSON();
        } catch (Exception e){
            log.error("Delete friendLink [{}] exception", id, e);
        }
        return JsonResult.fail(CodeType.SERVER_EXCEPTION).toJSON();
    }

    /**
     * 增加募捐记录
     */
    @PostMapping(value = "/addReward", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @PermissionCheck(value = "ROLE_SUPERADMIN")
    public String addReward(@RequestParam("file") MultipartFile file,
                            HttpServletRequest request,
                            Reward reward){

        try {
            //获得募捐时间
            String rewardDate = request.getParameter("reward-date");

            //上次募捐证书
            FileUtil fileUtil = new FileUtil();
            String filePath = this.getClass().getResource("/").getPath().substring(1) + "blogImg/";
            String fileContentType = file.getContentType();
            String fileExtension = fileContentType.substring(fileContentType.indexOf("/") + 1);
            TimeUtil timeUtil = new TimeUtil();
            String fileName = timeUtil.getLongTime() + "." + fileExtension;
            String subCatalog = "rewardRecord/" + new TimeUtil().getFormatDateForThree();
            String fileUrl = fileUtil.uploadFile(fileUtil.multipartFileToFile(file, filePath, fileName), subCatalog);

            reward.setRewardDate(timeUtil.stringToDateThree(rewardDate));
            //募捐去处处理
            if(reward.getFundraisingPlace().indexOf("《") == 0 && reward.getFundraisingPlace().indexOf("》") == reward.getFundraisingPlace().length()-1){
                reward.setFundraisingPlace(reward.getFundraisingPlace());
            } else {
                reward.setFundraisingPlace("《"+reward.getFundraisingPlace()+"》");
            }
            reward.setRewardUrl(fileUrl);
            if(reward.getRemarks() == null || StringUtil.BLANK.equals(reward.getRemarks().trim())){
                reward.setRemarks("无");
            }

            DataMap data = rewardService.save(reward);
            return JsonResult.build(data).toJSON();
        } catch (Exception e){
            log.error("Add reward [{}] exception", reward, e);
        }
        return JsonResult.fail(CodeType.SERVER_EXCEPTION).toJSON();
    }

    /**
     * 删除募捐记录
     */
    @GetMapping(value = "/deleteReward", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @PermissionCheck(value = "ROLE_SUPERADMIN")
    public  String deleteReward(@RequestParam("id") int id){
        try {
            DataMap data = rewardService.deleteReward(id);
            return JsonResult.build(data).toJSON();
        } catch (Exception e){
            log.error("Delete reward [{}] exception", id, e);
        }
        return JsonResult.fail(CodeType.SERVER_EXCEPTION).toJSON();
    }
}

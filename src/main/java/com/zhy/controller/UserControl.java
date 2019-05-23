package com.zhy.controller;

import com.zhy.model.User;
import com.zhy.service.*;
import com.zhy.utils.FileUtil;
import com.zhy.utils.TimeUtil;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.security.Principal;

/**
 * @author: zhangocean
 * @Date: 2018/7/20 20:56
 * Describe:
 */
@RestController
@SuppressWarnings("all")
public class UserControl {

    private Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    UserService userService;
    @Autowired
    CommentService commentService;
    @Autowired
    LeaveMessageService leaveMessageService;
    @Autowired
    PrivateWordService privateWordService;
    @Autowired
    RedisService redisService;

    /**
     * 上传头像
     */
    @PostMapping("/uploadHead")
    public JSONObject uploadHead(HttpServletRequest request,
                                 @AuthenticationPrincipal Principal principal){
        String username;
        try {
            username = principal.getName();
        } catch (NullPointerException e){
            JSONObject jsonObject = new JSONObject();
            logger.info("This user is not login");
            jsonObject.put("status",403);
            jsonObject.put("result","This user is not login");
            return jsonObject;
        }
        JSONObject jsonObject = new JSONObject();
        String img = request.getParameter("img");
        //获得上传文件的后缀名
        int index = img.indexOf(";base64,");
        String strFileExtendName = "." + img.substring(11,index);
        img = img.substring(index + 8);

        try {
            FileUtil fileUtil = new FileUtil();
            String filePath = this.getClass().getResource("/").getPath().substring(1) + "userImg/";
            TimeUtil timeUtil = new TimeUtil();
            File file = fileUtil.base64ToFile(filePath, img, timeUtil.getLongTime() + strFileExtendName);
            String url = fileUtil.uploadFile(file, "user/avatar/" + username);
            int userId = userService.findIdByUsername(username);
            userService.updateAvatarImgUrlById(url, userId);
            jsonObject = userService.getHeadPortraitUrl(userId);
        } catch (Exception e){
            e.printStackTrace();
            logger.error("更改头像失败",e.getMessage(),e);
            return jsonObject;
        }
        return jsonObject;
    }

    /**
     * 获得头像
     */
    @GetMapping("/getHeadPortraitUrl")
    public JSONObject getHeadPortraitUrl(@AuthenticationPrincipal Principal principal){
        String username = principal.getName();
        return userService.getHeadPortraitUrl(userService.findIdByUsername(username));
    }

    /**
     * 获得个人资料
     */
    @PostMapping("/getUserPersonalInfo")
    public JSONObject getUserPersonalInfo(@AuthenticationPrincipal Principal principal){
        String username = principal.getName();
        return userService.getUserPersonalInfoByUsername(username);
    }

    /**
     * 保存个人资料
     */
    @PostMapping("/savePersonalDate")
    public JSONObject savePersonalDate(User user,
                                       @AuthenticationPrincipal Principal principal){

        String username;
        try {
            username = principal.getName();
        } catch (NullPointerException e){
            JSONObject jsonObject = new JSONObject();
            logger.info("This user is not login");
            jsonObject.put("status",403);
            jsonObject.put("result","This user is not login");
            return jsonObject;
        }
        return userService.savePersonalDate(user, username);
    }

    /**
     * 获得该用户曾今的所有评论
     */
    @PostMapping("/getUserComment")
    public JSONObject getUserComment(@RequestParam("rows") String rows,
                                     @RequestParam("pageNum") String pageNum,
                                     @AuthenticationPrincipal Principal principal){
        String username;
        try {
            username = principal.getName();
        } catch (NullPointerException e){
            JSONObject jsonObject = new JSONObject();
            logger.info("This user is not login");
            jsonObject.put("status",403);
            jsonObject.put("result","This user is not login");
            return jsonObject;
        }
        return commentService.getUserComment(Integer.parseInt(rows), Integer.parseInt(pageNum), username);
    }

    /**
     * 获得该用户曾今的所有留言
     */
    @PostMapping("/getUserLeaveWord")
    public JSONObject getUserLeaveMessage(@RequestParam("rows") String rows,
                                          @RequestParam("pageNum") String pageNum,
                                          @AuthenticationPrincipal Principal principal){
        String username;
        try {
            username = principal.getName();
        } catch (NullPointerException e){
            JSONObject jsonObject = new JSONObject();
            logger.info("This user is not login");
            jsonObject.put("status",403);
            jsonObject.put("result","This user is not login");
            return jsonObject;
        }
        return leaveMessageService.getUserLeaveMessage(Integer.parseInt(rows), Integer.parseInt(pageNum), username);
    }

    /**
     * 发布悄悄话
     * @param privateWord 悄悄话内容
     */
    @PostMapping("/sendPrivateWord")
    public JSONObject sendPrivateWord(@RequestParam("privateWord") String privateWord,
                                      @AuthenticationPrincipal Principal principal){
        String username;
        try {
            username = principal.getName();
        } catch (NullPointerException e){
            JSONObject jsonObject = new JSONObject();
            logger.info("This user is not login");
            jsonObject.put("status",403);
            jsonObject.put("result","This user is not login");
            return jsonObject;
        }
        return privateWordService.publishPrivateWord(privateWord, username);
    }

    /**
     * 获得悄悄话
     * @param rows 一页大小
     * @param pageNum 当前页
     */
    @PostMapping("/getPrivateWordByPublisher")
    public JSONObject getPrivateWordByPublisher(@RequestParam("rows") String rows,
                                                @RequestParam("pageNum") String pageNum,
                                                @AuthenticationPrincipal Principal principal){
        String username;
        try {
            username = principal.getName();
        } catch (NullPointerException e){
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("status",403);
            jsonObject.put("result","This user is not login");
            return jsonObject;
        }
        return privateWordService.getPrivateWordByPublisher(username, Integer.parseInt(rows), Integer.parseInt(pageNum));
    }

    /**
     * 已读一条消息
     * @param id 消息的id
     * @param msgType 消息是评论消息还是留言消息  1-评论  2--留言
     */
    @GetMapping("/readThisMsg")
    @ResponseBody
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public int readThisMsg(@RequestParam("id") int id,
                           @RequestParam("msgType") int msgType,
                           @AuthenticationPrincipal Principal principal){
        redisService.readOneMsgOnRedis(userService.findIdByUsername(principal.getName()), msgType);
        if(msgType == 1){
            return commentService.readOneCommentRecord(id);
        } else {
            return leaveMessageService.readOneLeaveMessageRecord(id);
        }
    }

    /**
     * 已读所有消息
     * @param msgType 消息是评论消息还是留言消息  1-评论  2--留言
     */
    @GetMapping("/readAllMsg")
    @ResponseBody
    public JSONObject readAllMsg(@RequestParam("msgType") int msgType,
                                 @AuthenticationPrincipal Principal principal){
        String username;
        try {
            username = principal.getName();
        } catch (Exception e){
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("status",403);
            jsonObject.put("result","This user is not login");
            return jsonObject;
        }
        redisService.readAllMsgOnRedis(userService.findIdByUsername(username), msgType);
        if(msgType == 1){
            return commentService.readAllComment(username);
        } else {
            return leaveMessageService.readAllLeaveMessage(username);
        }
    }

    /**
     * 获得用户未读消息
     */
    @PostMapping("/getUserNews")
    @ResponseBody
    public JSONObject getUserNews(@AuthenticationPrincipal Principal principal){
        String username;
        try {
            username = principal.getName();
        } catch (Exception e){
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("status",403);
            jsonObject.put("result","This user is not login");
            return jsonObject;
        }
        return redisService.getUserNews(username);
    }

}

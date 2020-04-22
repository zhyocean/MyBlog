package com.zhy.controller;

import com.zhy.aspect.annotation.PermissionCheck;
import com.zhy.constant.CodeType;
import com.zhy.model.User;
import com.zhy.service.*;
import com.zhy.utils.DataMap;
import com.zhy.utils.FileUtil;
import com.zhy.utils.JsonResult;
import com.zhy.utils.TimeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.security.Principal;

/**
 * @author: zhangocean
 * @Date: 2018/7/20 20:56
 * Describe:
 */
@RestController
@Slf4j
public class UserControl {

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
    @PostMapping(value = "/uploadHead", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @PermissionCheck(value = "ROLE_USER")
    public String uploadHead(HttpServletRequest request,
                                 @AuthenticationPrincipal Principal principal){
        try {
            String username = principal.getName();
            String img = request.getParameter("img");
            //获得上传文件的后缀名
            int index = img.indexOf(";base64,");
            String strFileExtendName = "." + img.substring(11,index);
            img = img.substring(index + 8);

            FileUtil fileUtil = new FileUtil();
            String filePath = this.getClass().getResource("/").getPath().substring(1) + "userImg/";
            TimeUtil timeUtil = new TimeUtil();
            File file = fileUtil.base64ToFile(filePath, img, timeUtil.getLongTime() + strFileExtendName);
            String url = fileUtil.uploadFile(file, "user/avatar/" + username);
            int userId = userService.findIdByUsername(username);
            userService.updateAvatarImgUrlById(url, userId);

            DataMap data = userService.getHeadPortraitUrl(userId);
            return JsonResult.build(data).toJSON();

        } catch (Exception e){
            log.error("Upload head picture exception", e);
        }
        return JsonResult.fail(CodeType.SERVER_EXCEPTION).toJSON();
    }

    /**
     * 获得个人资料
     */
    @PostMapping(value = "/getUserPersonalInfo", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @PermissionCheck(value = "ROLE_USER")
    public String getUserPersonalInfo(@AuthenticationPrincipal Principal principal){
        String username = principal.getName();
        try {
            DataMap data = userService.getUserPersonalInfoByUsername(username);
            return JsonResult.build(data).toJSON();
        } catch (Exception e){
            log.error("[{}] get user personal info exception", username, e);
        }
        return JsonResult.fail(CodeType.SERVER_EXCEPTION).toJSON();
    }

    /**
     * 保存个人资料
     */
    @PostMapping(value = "/savePersonalDate", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @PermissionCheck(value = "ROLE_USER")
    public String savePersonalDate(User user, @AuthenticationPrincipal Principal principal){

        String username = principal.getName();
        try {
            DataMap data = userService.savePersonalDate(user, username);
            return JsonResult.build(data).toJSON();
        } catch (Exception e){
            log.error("[{}] save user info [{}] exception", username, user, e);
        }
        return JsonResult.fail(CodeType.SERVER_EXCEPTION).toJSON();
    }

    /**
     * 获得该用户曾今的所有评论
     */
    @PostMapping(value = "/getUserComment", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @PermissionCheck(value = "ROLE_USER")
    public String getUserComment(@RequestParam("rows") int rows,
                                     @RequestParam("pageNum") int pageNum,
                                     @AuthenticationPrincipal Principal principal){
        String username = principal.getName();
        try {
            DataMap data = commentService.getUserComment(rows, pageNum, username);
            return JsonResult.build(data).toJSON();
        } catch (Exception e){
            log.error("[{}] get comment exception", username, e);
        }
        return JsonResult.fail(CodeType.SERVER_EXCEPTION).toJSON();
    }

    /**
     * 获得该用户曾今的所有留言
     */
    @PostMapping(value = "/getUserLeaveWord", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @PermissionCheck(value = "ROLE_USER")
    public String getUserLeaveMessage(@RequestParam("rows") int rows,
                                          @RequestParam("pageNum") int pageNum,
                                          @AuthenticationPrincipal Principal principal){
        String username = principal.getName();
        try {
            DataMap data = leaveMessageService.getUserLeaveMessage(rows, pageNum, username);
            return JsonResult.build(data).toJSON();
        } catch (Exception e){
            log.error("[{}] get leaveWord exception", username, e);
        }
        return JsonResult.fail(CodeType.SERVER_EXCEPTION).toJSON();
    }

    /**
     * 发布悄悄话
     * @param privateWord 悄悄话内容
     */
    @PostMapping(value = "/sendPrivateWord", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @PermissionCheck(value = "ROLE_USER")
    public String sendPrivateWord(@RequestParam("privateWord") String privateWord,
                                      @AuthenticationPrincipal Principal principal){
        String username = principal.getName();
        try {
            DataMap data = privateWordService.publishPrivateWord(privateWord, username);
            return JsonResult.build(data).toJSON();
        } catch (Exception e){
            log.error("[{}] send private Word exception", username, e);
        }
        return JsonResult.fail(CodeType.SERVER_EXCEPTION).toJSON();
    }

    /**
     * 获得悄悄话
     * @param rows 一页大小
     * @param pageNum 当前页
     */
    @PostMapping(value = "/getPrivateWordByPublisher", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @PermissionCheck(value = "ROLE_USER")
    public String getPrivateWordByPublisher(@RequestParam("rows") int rows,
                                                @RequestParam("pageNum") int pageNum,
                                                @AuthenticationPrincipal Principal principal){
        String username = principal.getName();
        try {
            DataMap data = privateWordService.getPrivateWordByPublisher(username, rows, pageNum);
            return JsonResult.build(data).toJSON();
        } catch (Exception e){
            log.error("[{}] get private word exception", username, e);
        }
        return JsonResult.fail(CodeType.SERVER_EXCEPTION).toJSON();
    }

    /**
     * 已读一条消息
     * @param id 消息的id
     * @param msgType 消息是评论消息还是留言消息  1-评论  2--留言
     */
    @GetMapping(value = "/readThisMsg", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @PermissionCheck(value = "ROLE_USER")
    public String readThisMsg(@RequestParam("id") int id,
                           @RequestParam("msgType") int msgType,
                           @AuthenticationPrincipal Principal principal){
        String username = principal.getName();
        try {
            redisService.readOneMsgOnRedis(userService.findIdByUsername(username), msgType);
            if(msgType == 1){
                commentService.readOneCommentRecord(id);
            } else {
                leaveMessageService.readOneLeaveMessageRecord(id);
            }
            return JsonResult.success().toJSON();
        } catch (Exception e){
            log.error("[{}] read one type [{}] message exception", username, msgType, e);
        }
        return JsonResult.fail(CodeType.SERVER_EXCEPTION).toJSON();
    }

    /**
     * 已读所有消息
     * @param msgType 消息是评论消息还是留言消息  1-评论  2--留言
     */
    @GetMapping(value = "/readAllMsg", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @PermissionCheck(value = "ROLE_USER")
    public String readAllMsg(@RequestParam("msgType") int msgType,
                                 @AuthenticationPrincipal Principal principal){
        String username = principal.getName();
        try {
            redisService.readAllMsgOnRedis(userService.findIdByUsername(username), msgType);
            if(msgType == 1){
                commentService.readAllComment(username);
            } else {
                leaveMessageService.readAllLeaveMessage(username);
            }
            return JsonResult.success().toJSON();
        } catch (Exception e){
            log.error("[{}] read all type [{}] message exception", username, msgType, e);
        }
        return JsonResult.fail(CodeType.SERVER_EXCEPTION).toJSON();
    }

    /**
     * 获得用户未读消息
     */
    @PostMapping(value = "/getUserNews", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @PermissionCheck(value = "ROLE_USER")
    public String getUserNews(@AuthenticationPrincipal Principal principal){
        String username = principal.getName();
        try {
            DataMap data = redisService.getUserNews(username);
            return JsonResult.build(data).toJSON();
        } catch (Exception e){
            log.error("[{}] get user news exception", username, e);
        }
        return JsonResult.fail(CodeType.SERVER_EXCEPTION).toJSON();
    }

}

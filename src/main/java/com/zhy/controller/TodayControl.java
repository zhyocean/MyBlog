package com.zhy.controller;

import com.zhy.aspect.annotation.PermissionCheck;
import com.zhy.constant.CodeType;
import com.zhy.model.DailySpeech;
import com.zhy.service.TodayService;
import com.zhy.service.UserService;
import com.zhy.utils.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.List;

/**
 * @author: zhangocean
 * @Date: 2018/11/28 14:51
 * Describe: 我的日常
 */
@RestController
@Slf4j
public class TodayControl {

    @Autowired
    TodayService todayService;
    @Autowired
    UserService userService;

    @PostMapping(value = "/commitTodayWords", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @PermissionCheck(value = "ROLE_SUPERADMIN")
    public String publishISay(DailySpeech dailySpeech,
                                  HttpServletRequest request){
        try {
            List<MultipartFile> multipartFiles = ((MultipartHttpServletRequest)request).getFiles("picture");

            FileUtil fileUtil = new FileUtil();
            TimeUtil timeUtil = new TimeUtil();
            String picPath = this.getClass().getResource("/").getPath().substring(1) + "dailySpeechImg/";

            File file;
            StringBuilder picsUrlStrBuilder = new StringBuilder();
            //判断是否有上传的图片
            long publishTime;
            for (MultipartFile multipartFile : multipartFiles){
                //上传图片
                publishTime = System.currentTimeMillis();
                file = fileUtil.multipartFileToFile(multipartFile, picPath, publishTime + ".jpeg");
                if(file != null){
                    String url = fileUtil.uploadFile(file, "dailySpeech/" + timeUtil.getFormatDateForThree());
                    picsUrlStrBuilder.append(url).append(",");
                }
            }
            //设置上传的图片
            String picsUrlStr = picsUrlStrBuilder.toString();
            if(!StringUtil.BLANK.equals(picsUrlStr) && picsUrlStr.length() > 0){
                dailySpeech.setPicsUrl(picsUrlStr.substring(0, picsUrlStr.lastIndexOf(",")));
            }

            //替换taxtarea中的回车以及空格
            dailySpeech.setContent(dailySpeech.getContent().replaceAll(" ","&nbsp;").replaceAll("\r","<br/>"));

            DataMap data = todayService.publishISay(dailySpeech);
            return JsonResult.build(data).toJSON();
        } catch (Exception e){
            log.error("Publish dailySpeech [{}] exception", dailySpeech, e);
        }
        return JsonResult.fail(CodeType.SERVER_EXCEPTION).toJSON();
    }

    @PostMapping(value = "/getTodayInfo", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @PermissionCheck(value = "ROLE_SUPERADMIN")
    public String getTodayInfo(@RequestParam("rows") int rows,
                                   @RequestParam("pageNum") int pageNum){

        try {
            DataMap data = todayService.getTodayInfo(rows, pageNum);
            return JsonResult.build(data).toJSON();
        } catch (Exception e){
            log.error("Get today info exception", e);
        }
        return JsonResult.fail(CodeType.SERVER_EXCEPTION).toJSON();
    }

}

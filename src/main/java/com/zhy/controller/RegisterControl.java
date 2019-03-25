package com.zhy.controller;

import com.zhy.model.User;
import com.zhy.service.UserService;
import com.zhy.utils.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * @author: zhangocean
 * @Date: 2018/6/4 11:48
 * Describe:
 */
@Controller
public class RegisterControl {

    @Autowired
    UserService userService;

    @PostMapping("/register")
    @ResponseBody
    public String register(User user,
                            HttpServletRequest request){

        String authCode = request.getParameter("authCode");

        String trueMsgCode = (String) request.getSession().getAttribute("trueMsgCode");
        String msgCodePhone = (String) request.getSession().getAttribute("msgCodePhone");

        //判断验证码是否正确
        if(!authCode.equals(trueMsgCode)){
            return "0";
        }
        if(!msgCodePhone.equals(user.getPhone())){
            return "5";
        }
        //判断用户名是否存在
        if(userService.usernameIsExit(user.getUsername())){
            return "3";
        }
        //注册时对密码进行MD5加密
        MD5Util md5Util = new MD5Util();
        user.setPassword(md5Util.encode(user.getPassword()));
        return userService.insert(user);
    }

}

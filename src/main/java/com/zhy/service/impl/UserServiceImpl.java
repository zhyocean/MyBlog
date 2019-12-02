package com.zhy.service.impl;

import com.zhy.constant.CodeType;
import com.zhy.constant.RoleConstant;
import com.zhy.mapper.UserMapper;
import com.zhy.model.User;
import com.zhy.service.UserService;
import com.zhy.utils.DataMap;
import com.zhy.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: zhangocean
 * @Date: 2018/6/4 15:56
 * Describe: user表接口具体业务逻辑
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;

    @Override
    public User findUserByPhone(String phone) {
        return userMapper.findUserByPhone(phone);
    }

    @Override
    public String findUsernameById(int id) {
        return userMapper.findUsernameById(id);
    }

    @Override
    public DataMap insert(User user) {

        user.setUsername(user.getUsername().trim().replaceAll(" ", StringUtil.BLANK));
        String username = user.getUsername();

        if(username.length() > 35 || StringUtil.BLANK.equals(username)){
            return DataMap.fail(CodeType.USERNAME_FORMAT_ERROR);
        }
        if(userIsExist(user.getPhone())){
            return DataMap.fail(CodeType.PHONE_EXIST);
        }
        if("male".equals(user.getGender())){
            user.setAvatarImgUrl("https://zhy-myblog.oss-cn-shenzhen.aliyuncs.com/public/user/avatar/noLogin_male.jpg");
        } else {
            user.setAvatarImgUrl("https://zhy-myblog.oss-cn-shenzhen.aliyuncs.com/public/user/avatar/noLogin_female.jpg");
        }
        userMapper.save(user);
        int userId = userMapper.findUserIdByPhone(user.getPhone());
        insertRole(userId, RoleConstant.ROLE_USER);
        return DataMap.success();
    }

    @Override
    public int findUserIdByPhone(String phone) {
        return 0;
    }

    @Override
    public void updatePasswordByPhone(String phone, String password) {
        userMapper.updatePassword(phone, password);
//        密码修改成功后注销当前用户
        SecurityContextHolder.getContext().setAuthentication(null);
    }

    @Override
    public String findPhoneByUsername(String username) {
        return userMapper.findPhoneByUsername(username);
    }

    @Override
    public int findIdByUsername(String username) {
        return userMapper.findIdByUsername(username);
    }

    @Override
    public User findUsernameByPhone(String phone) {
        return userMapper.findUsernameByPhone(phone);
    }

    @Override
    public void updateRecentlyLanded(String username, String recentlyLanded) {
        String phone = userMapper.findPhoneByUsername(username);
        userMapper.updateRecentlyLanded(phone, recentlyLanded);
    }

    @Override
    public boolean usernameIsExist(String username) {
        User user = userMapper.findUsernameByUsername(username);
        return user != null;
    }

    @Override
    public boolean isSuperAdmin(String phone) {
        int userId = userMapper.findUserIdByPhone(phone);
        List<Object> roleIds = userMapper.findRoleIdByUserId(userId);

        for(Object i : roleIds){
            if((int)i == 3){
                return true;
            }
        }
        return false;
    }

    @Override
    public void updateAvatarImgUrlById(String avatarImgUrl, int id) {
        userMapper.updateAvatarImgUrlById(avatarImgUrl, id);
    }

    @Override
    public DataMap getHeadPortraitUrl(int id) {
        String avatarImgUrl = userMapper.getHeadPortraitUrl(id);
        return DataMap.success().setData(avatarImgUrl);
    }

    @Override
    public DataMap getUserPersonalInfoByUsername(String username) {
        User user = userMapper.getUserPersonalInfoByUsername(username);
        return DataMap.success().setData(user);
    }

    @Override
    public DataMap savePersonalDate(User user, String username) {

        user.setUsername(user.getUsername().trim().replaceAll(" ", StringUtil.BLANK));
        String newName = user.getUsername();
        if(newName.length() > StringUtil.USERNAME_MAX_LENGTH){
            return DataMap.fail(CodeType.USERNAME_TOO_LANG);
        } else if (StringUtil.BLANK.equals(newName)){
            return DataMap.fail(CodeType.USERNAME_BLANK);
        }

        int status;
        //改了昵称
        if(!newName.equals(username)){
            if(usernameIsExist(newName)){
                return DataMap.fail(CodeType.USERNAME_EXIST);
            }
            status = CodeType.HAS_MODIFY_USERNAME.getCode();
            //注销当前登录用户
            SecurityContextHolder.getContext().setAuthentication(null);
        }
        //没改昵称
        else {
            status = CodeType.NOT_MODIFY_USERNAME.getCode();
        }
        userMapper.savePersonalDate(user, username);

        return DataMap.success(status);
    }

    @Override
    public String getHeadPortraitUrlByUserId(int userId) {
        return userMapper.getHeadPortraitUrl(userId);
    }

    @Override
    public int countUserNum() {
        return userMapper.countUserNum();
    }

    /**
     * 增加用户权限
     * @param userId 用户id
     * @param roleId 权限id
     */
    private void insertRole(int userId, int roleId) {
        userMapper.saveRole(userId, roleId);
    }

    /**
     * 通过手机号判断用户是否存在
     * @param phone 手机号
     * @return true--存在  false--不存在
     */
    private boolean userIsExist(String phone){
        User user = userMapper.findUserByPhone(phone);
        return user != null;
    }
}

package com.zhy.service;

import com.zhy.model.User;
import com.zhy.utils.DataMap;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author: zhangocean
 * @Date: 2018/6/4 15:54
 * Describe: user业务操作
 */
public interface UserService {

    /**
     *  通过手机号查找注册用户
     * @param phone 手机号
     * @return 用户
     */
    User findUserByPhone(String phone);

    /**
     * 通过id查找用户名
     * @param id
     * @return
     */
    String findUsernameById(int id);

    /**
     * 注册用户
     */
    @Transactional
    DataMap insert(User user);

    /**
     * 通过手机号查找用户id
     * @param phone 手机号
     * @return 用户id
     */
    int findUserIdByPhone(String phone);

    /**
     * 通过手机号修改密码
     * @param phone 手机号
     * @param password 密码
     */
    void updatePasswordByPhone(String phone, String password);

    /**
     * 通过用户名获得手机号
     * @param username 用户名
     * @return 手机号
     */
    String findPhoneByUsername(String username);

    /**
     * 通过用户名查找id
     * @param username
     * @return
     */
    int findIdByUsername(String username);

    /**
     * 通过手机号查找用户名
     * @param phone 手机号
     * @return 用户名
     */
    User findUsernameByPhone(String phone);

    /**
     * 更新最近登录时间
     * @param username 用户名
     * @param recentlyLanded 最近登录时间
     */
    void updateRecentlyLanded(String username, String recentlyLanded);

    /**
     * 判断用户名是否存在
     * @param username 用户名
     * @return true--存在  false--不存在
     */
    boolean usernameIsExist(String username);

    /**
     * 通过手机号判断是否为超级用户
     * @param phone 手机号
     * @return true--超级管理员  false--非超级管理员
     */
    boolean isSuperAdmin(String phone);

    /**
     * 更改头像
     * @param avatarImgUrl 头像地址
     */
    @Transactional
    void updateAvatarImgUrlById(String avatarImgUrl, int id);

    /**
     * 获得头像url
     */
    DataMap getHeadPortraitUrl(int id);

    /**
     * 获得用户个人信息
     * @return
     */
    DataMap getUserPersonalInfoByUsername(String username);

    /**
     * 保存用户个人信息
     * @param user 个人信息
     * @param username 当前更改的用户
     * @return
     */
    DataMap savePersonalDate(User user, String username);

    /**
     * 获得用户头像的地址
     * @return 头像的url
     */
    String getHeadPortraitUrlByUserId(int userId);

    /**
     * 统计总用户量
     * @return
     */
    int countUserNum();
}

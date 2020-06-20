package com.zhy.constant;

/**
 * @author: zhangocean
 * @Date: 2019/11/18 15:51
 * Describe: 状态返回码
 */
public enum CodeType {

    /**
     * 状态码
     */
    SUCCESS_STATUS(0, "成功"),

    USER_NOT_LOGIN(101, "用户未登录"),
    PERMISSION_VERIFY_FAIL(102, "权限验证失败"),
    SERVER_EXCEPTION(103, "服务器异常"),

    DELETE_ARTICLE_FAIL(201, "删除文章失败"),
    ARTICLE_HAS_THUMBS_UP(202, "文章已经点过赞了"),
    ARTICLE_NOT_EXIST(203, "文章不存在"),
    PUBLISH_ARTICLE_NO_PERMISSION(204, "发表文章没有权限"),

    FIND_TAGS_CLOUD(301, "获得所有标签成功"),
    FIND_ARTICLE_BY_TAG(302, "通过标签获得文章成功"),

    ADD_CATEGORY_SUCCESS(401, "添加分类成功"),
    CATEGORY_EXIST(402, "分类已存在"),
    DELETE_CATEGORY_SUCCESS(403, "删除分类成功"),
    CATEGORY_NOT_EXIST(404, "分类不存在"),
    CATEGORY_HAS_ARTICLE(405, "分类下存在文章，删除失败"),

    USERNAME_TOO_LANG(501, "用户名太长"),
    USERNAME_BLANK(502, "用户名为空"),
    HAS_MODIFY_USERNAME(503, "修改个人信息成功，并且修改了用户名"),
    NOT_MODIFY_USERNAME(504, "修改个人信息成功，但没有修改用户名"),
    USERNAME_EXIST(505, "用户名已存在"),
    USERNAME_NOT_EXIST(506, "用户名不存在"),
    USERNAME_FORMAT_ERROR(507, "用户名长度过长或格式不正确"),

    ADD_FRIEND_LINK_SUCCESS(601, "添加友链成功"),
    FRIEND_LINK_EXIST(602, "友链已存在"),
    UPDATE_FRIEND_LINK_SUCCESS(603, "更新友链成功"),
    DELETE_FRIEND_LINK_SUCCESS(604, "删除友链成功"),

    ADD_REWARD_SUCCESS(701, "增加募捐记录成功"),
    DELETE_REWARD_SUCCESS(702, "删除募捐记录成功"),

    COMMENT_BLANK(801, "内容为空"),
    MESSAGE_HAS_THUMBS_UP(802, "已经点过赞了"),

    PHONE_ERROR(901, "手机号错误"),
    AUTH_CODE_ERROR(902, "验证码错误"),
    PHONE_EXIST(903, "手机号存在"),
    ;

    private int code;

    private String message;

    CodeType(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }


}

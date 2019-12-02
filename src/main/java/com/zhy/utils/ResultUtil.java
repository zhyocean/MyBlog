package com.zhy.utils;

import com.zhy.model.Result;

/**
 * @author: zhangocean
 * @Date: 2019/5/18 19:13
 * Describe: 返回统一格式封装工具
 */
public class ResultUtil {

    public static Result success(String msg) {
        return success(200, msg);
    }

    public static Result success(Integer code, String msg) {
        Result result = new Result();
        result.setCode(code);
        result.setMsg(msg);
        return result;
    }

    public static Result success(Integer code, Object object, String msg) {
        Result result = new Result();
        result.setCode(code);
        result.setData(object);
        result.setMsg(msg);
        return result;
    }

    public static Result error(Integer code, String msg) {
        Result result = new Result();
        result.setCode(code);
        result.setMsg(msg);
        return result;
    }
}

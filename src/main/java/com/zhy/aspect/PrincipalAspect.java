package com.zhy.aspect;

import com.zhy.aspect.annotation.PermissionCheck;
import com.zhy.constant.CodeType;
import com.zhy.utils.JsonResult;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Collection;

/**
 * @author: zhangocean
 * @Date: 2019/11/1 13:21
 * Describe: 定义切面，拦截所有需要登录操作的controller接口
 */
@Aspect
@Component
@Slf4j
public class PrincipalAspect {

    public static final String ANONYMOUS_USER = "anonymousUser";

    @Pointcut("execution(public * com.zhy.controller..*(..))")
    public void login(){

    }

    @Around("login() && @annotation(permissionCheck)")
    public Object principalAround(ProceedingJoinPoint pjp, PermissionCheck permissionCheck) throws Throwable {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String loginName = auth.getName();
        //没有登录
        if(loginName.equals(ANONYMOUS_USER)){
            return JsonResult.fail(CodeType.USER_NOT_LOGIN).toJSON();
        }
        //接口权限拦截
        Collection<? extends GrantedAuthority> authority =  auth.getAuthorities();
        String value = permissionCheck.value();
        for(GrantedAuthority g : authority){
            if(g.getAuthority().equals(value)){
                return pjp.proceed();
            }
        }
        log.error("[{}] has no access to the [{}] method ", loginName, pjp.getSignature().getName());
        return JsonResult.fail(CodeType.PERMISSION_VERIFY_FAIL).toJSON();
    }

}

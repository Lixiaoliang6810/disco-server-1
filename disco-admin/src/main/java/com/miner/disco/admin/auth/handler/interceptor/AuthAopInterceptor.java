package com.miner.disco.admin.auth.handler.interceptor;

import org.apache.shiro.spring.security.interceptor.AopAllianceAnnotationsAuthorizingMethodInterceptor;

/**
 * @author: chencx
 * @create: 2018-11-20
 **/
public class AuthAopInterceptor extends AopAllianceAnnotationsAuthorizingMethodInterceptor {
    public AuthAopInterceptor() {
        super();
        // 添加自定义的注解拦截器
        this.methodInterceptors.add(new AuthMethodInterceptor());
    }
}

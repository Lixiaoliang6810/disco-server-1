package com.miner.disco.admin.configurer.shiro;

import com.miner.disco.admin.auth.annotation.Module;
import com.miner.disco.admin.auth.annotation.NoAuth;
import com.miner.disco.admin.auth.handler.interceptor.AuthAopInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;

import java.lang.reflect.Method;

/**
 * @author: chencx
 * @create: 2018-11-20 23:27
 **/
@Slf4j
public class ShiroAdvisor extends AuthorizationAttributeSourceAdvisor {

    public ShiroAdvisor() {
        setAdvice(new AuthAopInterceptor());
    }

    @SuppressWarnings({"unchecked"})
    @Override
    public boolean matches(Method method, Class targetClass) {
        Method m = method;
        if (targetClass != null) {
            return null != targetClass.getAnnotation(Module.class) && noAuth(method);
        }
        return super.matches(method, null);
    }

    private boolean noAuth(Method method) {
        return null == method.getAnnotation(NoAuth.class);
    }
}
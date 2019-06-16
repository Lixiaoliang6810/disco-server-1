package com.miner.disco.admin.auth.handler.interceptor;

import com.miner.disco.admin.auth.DefaultFunc;
import com.miner.disco.admin.auth.annotation.Func;
import com.miner.disco.admin.auth.annotation.Module;
import com.miner.disco.admin.auth.handler.AuthHandler;
import org.apache.shiro.aop.MethodInvocation;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.aop.AuthorizingAnnotationMethodInterceptor;
import org.springframework.util.ObjectUtils;

/**
 * @author: chencx
 * @create: 2018-11-20 23:21
 **/
public class AuthMethodInterceptor extends AuthorizingAnnotationMethodInterceptor {

    public AuthMethodInterceptor() {
        super(new AuthHandler(new DefaultFunc()));
    }

    public void assertAuthorized(MethodInvocation mi) throws AuthorizationException {
        // 验证权限
        try {
            Func func = mi.getMethod().getAnnotation(Func.class);
            Module module = mi.getMethod().getDeclaringClass().getAnnotation(Module.class);
            if ((ObjectUtils.isEmpty(func))){
                ((AuthHandler) this.getHandler()).assertAuthorized(module);
            }else {
                ((AuthHandler) this.getHandler()).assertAuthorized(module, func);
            }
        } catch (AuthorizationException ae) {
            if (ae.getCause() == null) {
                ae.initCause(new AuthorizationException("method no auth : " + mi.getMethod()));
            }
            throw ae;
        }
    }

}

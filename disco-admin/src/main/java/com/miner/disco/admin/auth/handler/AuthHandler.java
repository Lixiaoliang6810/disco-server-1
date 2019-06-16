package com.miner.disco.admin.auth.handler;

import com.google.common.collect.Sets;
import com.miner.disco.admin.auth.BasicFunction;
import com.miner.disco.admin.auth.BasicModule;
import com.miner.disco.admin.auth.annotation.Func;
import com.miner.disco.admin.auth.annotation.Module;
import com.miner.disco.admin.exception.AdminBuzException;
import com.miner.disco.admin.exception.AdminBuzExceptionCode;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.aop.AuthorizingAnnotationHandler;
import org.apache.shiro.subject.Subject;
import org.springframework.lang.NonNull;
import org.springframework.util.CollectionUtils;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.Set;

/**
 * @author: chencx
 * @create: 2018-11-20 22:59
 **/
@Slf4j
public class AuthHandler extends AuthorizingAnnotationHandler {

    private BasicFunction defaultFunc;

    public AuthHandler(BasicFunction defaultFunc) {
        super(Module.class);
        this.defaultFunc = defaultFunc;
    }

    /**
     * 自定义验证规则
     * @param module
     * @param func
     * @throws AuthorizationException
     */
    public void assertAuthorized(@NonNull Module module, @NonNull Func func) throws AuthorizationException {
        Set<String> modules = getModuleValue(module);
        Class<? extends BasicFunction> funcClz = func.value();
        Class<? extends BasicModule> target = func.target();

        String funcCode;

        try {
            funcCode = funcClz.newInstance().getCode();
            //指定模块
            if (target != BasicModule.class){
                    String targetModule = target.newInstance().getCode();
                    if (!modules.contains(targetModule)){
                        log.warn("{} not found in the controller module annotation.", target);
                        throw new AuthorizationException("no auth");
                    }
                    String perms = targetModule.concat(funcCode);
                    getSubject().checkPermissions(perms);
                    return;
            }
        } catch (Exception e) {
            log.error("method com.miner.shiro.handler.AuthHandler.assertAuthorized(com.miner.shiro.auth.annotation.Module, com.miner.shiro.auth.annotation.Func) newInstance error. ", e);
            throw new AdminBuzException(AdminBuzExceptionCode.DATA_CONVERSION_ERROR.getCode(), AdminBuzExceptionCode.DATA_CONVERSION_ERROR.getMessage());
        }

        if (module.logical().equals(Logical.OR)){
            Subject subject = this.getSubject();
            boolean hasAtLeastOnePermission = false;
            for(String m : modules){
                if(subject.isPermitted(m.concat("-").concat(funcCode))){
                    hasAtLeastOnePermission=true;
                    break;
                }
            }
            if (!hasAtLeastOnePermission) throw new AuthorizationException("no auth");
        }

        if (module.logical().equals(Logical.AND)){
            Set<String> permis = Sets.newHashSet();
            for (String m : modules){
                permis.add(m.concat("-").concat(funcCode));
            }
            getSubject().checkPermissions(permis.toArray(new String[permis.size()]));
            return;
        }

    }


    /**
     * 单模块验证
     * 使用org.apache.shiro.authz.aop.PermissionAnnotationHandler验证
     * @param a
     * @throws AuthorizationException
     */
    @Override
    public void assertAuthorized(Annotation a) throws AuthorizationException {
        if (!(a instanceof Module)) return;

        Module rpAnnotation = (Module) a;
        Set<String> permsList = getModuleValue(a);
        if (CollectionUtils.isEmpty(permsList)){
            throw new AdminBuzException(AdminBuzExceptionCode.DATA_CONVERSION_ERROR.getCode(), AdminBuzExceptionCode.DATA_CONVERSION_ERROR.getMessage());
        }
        Set<String> perms = Sets.newHashSetWithExpectedSize(permsList.size());
        permsList.forEach(per -> {perms.add(per.concat("-").concat(defaultFunc.getCode()));});
        String[] permArr = perms.toArray(new String[perms.size()]);
        Subject subject = getSubject();

        if (permArr.length == 1) {
            subject.checkPermission(permArr[0]);
            return;
        }
        if (Logical.AND.equals(rpAnnotation.logical())) {
            getSubject().checkPermissions(permArr);
            return;
        }
        if (Logical.OR.equals(rpAnnotation.logical())) {
            // Avoid processing exceptions unnecessarily - "delay" throwing the exception by calling hasRole first
            boolean hasAtLeastOnePermission = false;
            for (String permission : perms) if (getSubject().isPermitted(permission)) hasAtLeastOnePermission = true;
            // Cause the exception if none of the role match, note that the exception message will be a bit misleading
            if (!hasAtLeastOnePermission) getSubject().checkPermission(permArr[0]);

        }
    }

    protected Set<String> getModuleValue(Annotation a) {
        Module rpAnnotation = (Module) a;
        Class<? extends BasicModule>[] baseClz = rpAnnotation.value();
        Set<String> perms = Sets.newHashSetWithExpectedSize(baseClz.length);
        Arrays.asList(baseClz).forEach(base -> {
            try {
                perms.add(base.newInstance().getCode());
            } catch (Exception e) {
                log.error("{} newInstance error. ",baseClz, e);
                perms.clear();
                return;
            }
        });
        return perms;
    }

}

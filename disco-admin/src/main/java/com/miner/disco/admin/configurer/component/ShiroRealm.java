package com.miner.disco.admin.configurer.component;

import com.miner.disco.admin.constant.RestConst;
import com.miner.disco.admin.exception.AdminBuzException;
import com.miner.disco.admin.exception.AdminBuzExceptionCode;
import com.miner.disco.admin.model.LoginModule;
import com.miner.disco.admin.service.admin.UserService;
import com.miner.disco.basic.constants.StateEnum;
import com.miner.disco.basic.util.DtoTransition;
import com.miner.disco.pojo.SysUser;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;

/**
 * 自定义权限匹配和账号密码匹配
 * @author: chencx
 * @create: 2018-11-19
 **/
@Slf4j
public class ShiroRealm extends AuthorizingRealm{

    @Autowired
    private UserService userService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        log.info("权限配置-->ShiroRealm.doGetAuthorizationInfo()");
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        LoginModule loginModule = (LoginModule) principals.getPrimaryPrincipal();
       /* List<PermissionRequest> permissions =  userService.findPermissions(loginModule.getId());
        Set<String> permissCodes = Sets.newHashSet();
        permissions.forEach(permission -> {
            if (StringUtils.isNotBlank(permission.getModuleCode()) && StringUtils.isNotBlank(permission.getFuncCode())){
                permissCodes.add(permission.getModuleCode().concat("-").concat(permission.getFuncCode()));
            }
        });*/
        Set<String> permissions =  userService.findPermissionCodes(loginModule.getId());
        authorizationInfo.addStringPermissions(permissions);
        log.info("codes ========== {}", permissions);
        return authorizationInfo;
    }

    /*主要是用来进行身份认证的，也就是说验证用户输入的账号和密码是否正确。*/
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token)
            throws AuthenticationException {
        log.info("ShiroRealm.doGetAuthenticationInfo()");
        //获取用户的输入的账号.
        String loginName = (String) token.getPrincipal();
        log.info(token.getCredentials().toString());

        SysUser sysUser = userService.findByLoginName(loginName);

        if (sysUser == null){
            //user non-existent
            throw new AdminBuzException(AdminBuzExceptionCode.USER_NON_EXISTS.getCode(), AdminBuzExceptionCode.USER_NON_EXISTS.getMessage());
        }

        if (sysUser.getStatus() == StateEnum.DISABLE.getKey()){
            throw new AdminBuzException(AdminBuzExceptionCode.USER_IS_DISABLE.getCode(), AdminBuzExceptionCode.USER_IS_DISABLE.getMessage());
        }

        LoginModule loginModule = (LoginModule) DtoTransition.trans(LoginModule.class, sysUser);
        loginModule.setLoginName(loginName);

        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
                loginModule,
                sysUser.getPassword(), //密码
                ByteSource.Util.bytes(sysUser.getSalt().concat(sysUser.getMobile())),//
                getName()  //realm name
        );

        return authenticationInfo;
    }

    public static void main(String[] args) {
//        String salt = new SecureRandomNumberGenerator().nextBytes().toHex();
        String salt = "082ec6abb2220e87edc4ea83b198bc0e";
        String password = new Md5Hash("123123",salt + "18080086868", RestConst.MD5_HASH_ITERATIONS).toString();
//        System.out.println(salt);
        System.out.println(password);
    }

}

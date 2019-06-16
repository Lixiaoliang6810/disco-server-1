package com.miner.disco.admin.exception;

import com.miner.disco.basic.model.response.ViewData;
import com.miner.disco.boot.support.GlobalExceptionHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authz.AuthorizationException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Objects;

@Slf4j
@ResponseBody
@ControllerAdvice
public class AdminGlobalExceptionHandler extends GlobalExceptionHandler {

    /**
     * 授权异常
     * <p>
     * org.apache.shiro.authz.UnauthenticatedException  授权异常
     * org.apache.shiro.authz.HostUnauthorizedException 没有访问权限
     * org.apache.shiro.authz.UnauthorizedException     没有访问权限
     * org.apache.shiro.authz.AuthorizationException    上面异常的父类
     *
     * @param ex 没有权限的异常
     * @return ModelAndView
     */
    @ExceptionHandler(value = {AuthorizationException.class})
    public ViewData authorizationExceptionHandler(AuthorizationException ex) {
        log.warn("no auth exception => {}", ex.getMessage());
        return ViewData.builder().status(AuthErrorCode.NO_AUTH.getCode()).message(AuthErrorCode.NO_AUTH.getMessage()).build();
    }

    /**
     * 认证异常
     * <p>
     * org.apache.shiro.authc.pam.UnsupportedTokenException 身份令牌异常，不支持的身份令牌
     * org.apache.shiro.authc.UnknownAccountException       未知账户/没找到帐号,登录失败
     * org.apache.shiro.authc.LockedAccountException        帐号锁定
     * org.apache.shiro.authz.DisabledAccountException      用户禁用
     * org.apache.shiro.authc.ExcessiveAttemptsException    登录重试次数，超限。只允许在一段时间内允许有一定数量的认证尝试
     * org.apache.shiro.authc.ConcurrentAccessException     一个用户多次登录异常：不允许多次登录，只能登录一次 。即不允许多处登录
     * org.apache.shiro.authz.AccountException              账户异常
     * org.apache.shiro.authz.ExpiredCredentialsException   过期的凭据异常
     * org.apache.shiro.authc.IncorrectCredentialsException 错误的凭据异常
     * org.apache.shiro.authc.CredentialsException          凭据异常
     * org.apache.shiro.authc.AuthenticationException       上面异常的父类
     *
     * @param ex 没有权限的异常
     */
    @ExceptionHandler(value = {AuthenticationException.class})
    public ViewData authenticationExceptionHanlder(AuthenticationException ex) {
        log.warn("authentication exception => {}", ex.getMessage());
        return ViewData.builder().status(AuthErrorCode.AUTHENTICATION_ERROR.getCode()).message(AuthErrorCode.AUTHENTICATION_ERROR.getMessage()).build();
    }

    @Override
    @ExceptionHandler(value = BindException.class)
    public ViewData bindException(BindException exception) {
        BindingResult bindingResult = exception.getBindingResult();
        return ViewData.builder().status(AdminBuzExceptionCode.DATA_VALID_REJECT.getCode())
                .message(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage()).build();
    }

}

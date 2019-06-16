package com.miner.disco.admin.controller;

import com.miner.disco.admin.exception.AuthErrorCode;
import com.miner.disco.admin.model.LoginModule;
import com.miner.disco.admin.model.request.system.LoginRequest;
import com.miner.disco.admin.model.response.system.UserDetailsResponse;
import com.miner.disco.admin.service.admin.UserService;
import com.miner.disco.basic.model.response.ViewData;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: chencx
 * @create: 2019-01-08
 **/
@RestController
public class LoginController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ViewData login(@Validated LoginRequest login){
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(
                login.getUsername(),
                login.getPassword());
        subject.login(usernamePasswordToken);
        return ViewData.builder().data(subject.getSession().getId()).message("登陆成功").build();
    }

    @PostMapping("/cancel")
    public ViewData cancel() {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return ViewData.builder().message("注销成功").build();
    }

    @RequestMapping(value = "/unauth")
    public ViewData unauth() {
        return ViewData.builder().status(AuthErrorCode.NO_SESSION.getCode()).message(AuthErrorCode.NO_SESSION.getMessage()).build();
    }

    /**
     * 被踢出后跳转方法
     * @return
     */
    @RequestMapping("/kickout")
    public ViewData kickout(){
        return ViewData.builder().status(AuthErrorCode.ALREADY_LOGIN.getCode()).message(AuthErrorCode.ALREADY_LOGIN.getMessage()).build();
    }

    @GetMapping("/details")
    public ViewData details(){
        Subject subject = SecurityUtils.getSubject();
        LoginModule loginModule = (LoginModule) subject.getPrincipal();
        UserDetailsResponse userDetailsResponse = userService.details(loginModule.getId());
        userDetailsResponse.setUsername(loginModule.getNickname());
        return ViewData.builder().data(userDetailsResponse).build();
    }

}

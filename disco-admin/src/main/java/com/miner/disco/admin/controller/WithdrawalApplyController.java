package com.miner.disco.admin.controller;

import com.miner.disco.admin.auth.annotation.Func;
import com.miner.disco.admin.auth.annotation.Module;
import com.miner.disco.admin.constant.function.Review;
import com.miner.disco.admin.constant.module.member.WithdrawalManager;
import com.miner.disco.admin.model.LoginModule;
import com.miner.disco.admin.model.request.member.WithdrawalApplyRequest;
import com.miner.disco.admin.service.member.WithdrawalApplyService;
import com.miner.disco.basic.model.response.PageResponse;
import com.miner.disco.basic.model.response.ViewData;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: chencx
 * @create: 2019-01-17
 **/
@RestController
@Module(WithdrawalManager.class)
public class WithdrawalApplyController {

    @Autowired
    private WithdrawalApplyService withdrawalApplyService;

    @GetMapping("/member/withdrawal/apply")
    public ViewData list(WithdrawalApplyRequest search){
       PageResponse pageResponse =  withdrawalApplyService.findPage(search);
       return ViewData.builder().data(pageResponse).message("提现申请").build();
    }

    @PutMapping("/member/withdrawal/review")
    @Func(Review.class)
    public ViewData list(@RequestParam("id") Long id, @RequestParam("status") Integer status){
        Subject subject = SecurityUtils.getSubject();
        LoginModule loginModule = (LoginModule) subject.getPrincipal();
        String mes = withdrawalApplyService.review(loginModule.getId(),id, status);
        return ViewData.builder().message(mes).build();
    }

}

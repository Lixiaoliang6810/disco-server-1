package com.miner.disco.front.controller;

import com.miner.disco.basic.model.response.ViewData;
import com.miner.disco.front.consts.Const;
import com.miner.disco.front.model.request.WithdrawalApplyRequest;
import com.miner.disco.front.oauth.model.CustomUserDetails;
import com.miner.disco.front.service.WithdrawalApplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Created by lubycoder@163.com 2019/1/9
 */
@RestController
public class WithdrawalApplyController {

    @Autowired
    private WithdrawalApplyService withdrawalApplyService;


    @PostMapping(value = "/withdrawal/apply", headers = Const.API_VERSION_1_0_0)
    public ViewData apply(@AuthenticationPrincipal OAuth2Authentication auth2Authentication,
                          WithdrawalApplyRequest request) {
        Long uid = ((CustomUserDetails) auth2Authentication.getPrincipal()).getId();
        request.setUserId(uid);
        withdrawalApplyService.apply(request);
        return ViewData.builder().message("申请成功").build();
    }

}

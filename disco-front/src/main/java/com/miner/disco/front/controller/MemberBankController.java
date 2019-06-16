package com.miner.disco.front.controller;

import com.miner.disco.basic.model.response.ViewData;
import com.miner.disco.front.consts.Const;
import com.miner.disco.front.model.request.MemberBankBindRequest;
import com.miner.disco.front.model.request.MemberBankListRequest;
import com.miner.disco.front.model.response.MemberBankListResponse;
import com.miner.disco.front.oauth.model.CustomUserDetails;
import com.miner.disco.front.service.MemberBankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Created by lubycoder@163.com 2019/1/14
 */
@RestController
public class MemberBankController {

    @Autowired
    private MemberBankService memberBankService;

    @PostMapping(value = "/member/bank/bind", headers = Const.API_VERSION_1_0_0)
    public ViewData bind(@AuthenticationPrincipal OAuth2Authentication oAuth2Authentication,
                         MemberBankBindRequest request) {
        Long uid = ((CustomUserDetails) oAuth2Authentication.getPrincipal()).getId();
        request.setUserId(uid);
        memberBankService.bind(request);
        return ViewData.builder().message("绑定成功").build();
    }

    @PostMapping(value = "/member/bank/unbind", headers = Const.API_VERSION_1_0_0)
    public ViewData unbind(@AuthenticationPrincipal OAuth2Authentication oAuth2Authentication,
                           @RequestParam(value = "bankId") Long bankId) {
        Long uid = ((CustomUserDetails) oAuth2Authentication.getPrincipal()).getId();
        memberBankService.unbind(uid, bankId);
        return ViewData.builder().message("解绑成功").build();
    }

    @GetMapping(value = "/member/bank/list", headers = Const.API_VERSION_1_0_0)
    public ViewData list(@AuthenticationPrincipal OAuth2Authentication oAuth2Authentication,
                         MemberBankListRequest request) {
        Long uid = ((CustomUserDetails) oAuth2Authentication.getPrincipal()).getId();
        request.setUserId(uid);
        List<MemberBankListResponse> response = memberBankService.list(request);
        return ViewData.builder().data(response).message("银行卡列表").build();
    }

}

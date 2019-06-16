package com.miner.disco.front.controller;

import com.miner.disco.basic.model.response.ViewData;
import com.miner.disco.front.consts.Const;
import com.miner.disco.front.model.request.MemberVipApplyRequest;
import com.miner.disco.front.model.request.VipBillListRequest;
import com.miner.disco.front.model.response.MemberVipApplyInfoResponse;
import com.miner.disco.front.model.response.VipBillListResponse;
import com.miner.disco.front.oauth.model.CustomUserDetails;
import com.miner.disco.front.service.VipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Created by lubycoder@163.com 2019/1/8
 */
@RestController
public class VipController {

    @Autowired
    private VipService vipService;

    @PostMapping(value = "/member/vip/apply", headers = Const.API_VERSION_1_0_0)
    public ViewData apply(@AuthenticationPrincipal OAuth2Authentication oAuth2Authentication,
                          MemberVipApplyRequest request) {
        Long uid = ((CustomUserDetails) oAuth2Authentication.getPrincipal()).getId();
        request.setUserId(uid);
        vipService.apply(request);
        return ViewData.builder().message("申请成功").build();
    }

    @GetMapping(value = "/member/vip/apply/info", headers = Const.API_VERSION_1_0_0)
    public ViewData info(@AuthenticationPrincipal OAuth2Authentication oAuth2Authentication) {
        Long uid = ((CustomUserDetails) oAuth2Authentication.getPrincipal()).getId();
        MemberVipApplyInfoResponse response = vipService.info(uid);
        return ViewData.builder().data(response).message("申请信息").build();
    }

    @GetMapping(value = "/member/vip/bill")
    public ViewData bill(@AuthenticationPrincipal OAuth2Authentication oAuth2Authentication,
                         VipBillListRequest request) {
        Long uid = ((CustomUserDetails) oAuth2Authentication.getPrincipal()).getId();
        request.setUserId(uid);
        List<VipBillListResponse> response = vipService.bill(request);
        return ViewData.builder().data(response).message("引导员业绩").build();
    }

}

package com.miner.disco.front.controller;

import com.miner.disco.basic.model.response.ViewData;
import com.miner.disco.front.consts.Const;
import com.miner.disco.front.model.request.CollectMerchantListRequest;
import com.miner.disco.front.model.response.CollectMerchantListResponse;
import com.miner.disco.front.oauth.model.CustomUserDetails;
import com.miner.disco.front.service.CollectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Created by lubycoder@163.com 2019/1/7
 */
@RestController
public class CollectController {

    @Autowired
    private CollectService collectService;

    @PostMapping(value = "/collect/merchant", headers = Const.API_VERSION_1_0_0)
    public ViewData collect(@AuthenticationPrincipal OAuth2Authentication oAuth2Authentication,
                            @RequestParam("merchantId") Long merchantId) {
        Long uid = ((CustomUserDetails) oAuth2Authentication.getPrincipal()).getId();
        collectService.collect(uid, merchantId);
        return ViewData.builder().message("收藏成功").build();
    }

    @PostMapping(value = "/collect/merchant/cancel", headers = Const.API_VERSION_1_0_0)
    public ViewData cancel(@AuthenticationPrincipal OAuth2Authentication oAuth2Authentication,
                           @RequestParam("merchantId") Long merchantId) {
        Long uid = ((CustomUserDetails) oAuth2Authentication.getPrincipal()).getId();
        collectService.cancel(uid, merchantId);
        return ViewData.builder().message("取消收藏").build();
    }

    @GetMapping(value = "/collect/merchant/list", headers = Const.API_VERSION_1_0_0)
    public ViewData list(@AuthenticationPrincipal OAuth2Authentication oAuth2Authentication,
                         CollectMerchantListRequest request) {
        Long uid = ((CustomUserDetails) oAuth2Authentication.getPrincipal()).getId();
        request.setUserId(uid);
        List<CollectMerchantListResponse> response = collectService.list(request);
        return ViewData.builder().data(response).message("收藏的商家").build();
    }

}

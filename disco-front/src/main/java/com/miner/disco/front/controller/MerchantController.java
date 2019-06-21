package com.miner.disco.front.controller;

import com.miner.disco.basic.model.response.ViewData;
import com.miner.disco.front.consts.Const;
import com.miner.disco.front.model.request.MerchantListRequest;
import com.miner.disco.front.model.response.MerchantDetailsResponse;
import com.miner.disco.front.model.response.MerchantListResponse;
import com.miner.disco.front.oauth.model.CustomUserDetails;
import com.miner.disco.front.service.MerchantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Created by lubycoder@163.com 2018/12/24
 */
@RestController
public class MerchantController {

    @Autowired
    private MerchantService merchantService;

    @GetMapping(value = "/merchant/details", headers = Const.API_VERSION_1_0_0)
    public ViewData details(@AuthenticationPrincipal OAuth2Authentication auth2Authentication,
                            @RequestParam("merchantId") Long merchantId) {
        Long uid =0L;
        if(auth2Authentication!=null){
            uid = ((CustomUserDetails) auth2Authentication.getPrincipal()).getId();
        }
        MerchantDetailsResponse detailsResponse = merchantService.details(uid, merchantId);
        return ViewData.builder().data(detailsResponse).message("商家详情").build();
    }

    @GetMapping(value = "/merchant/list", headers = Const.API_VERSION_1_0_0)
    public ViewData list(MerchantListRequest request) {
        List<MerchantListResponse> response = merchantService.list(request);
        return ViewData.builder().data(response).message("商家列表").build();
    }

}

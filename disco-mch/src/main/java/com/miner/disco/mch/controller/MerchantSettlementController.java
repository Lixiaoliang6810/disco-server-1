package com.miner.disco.mch.controller;

import com.miner.disco.basic.model.response.ViewData;
import com.miner.disco.mch.consts.Const;
import com.miner.disco.mch.model.request.MerchantSettlementApplyRequest;
import com.miner.disco.mch.model.request.MerchantSettlementRecordRequest;
import com.miner.disco.mch.model.response.MerchantSettlementRecordResponse;
import com.miner.disco.mch.oauth.model.CustomUserDetails;
import com.miner.disco.mch.service.MerchantSettlementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Created by lubycoder@163.com 2019-2-21
 */
@RestController
public class MerchantSettlementController {

    @Autowired
    private MerchantSettlementService merchantSettlementService;

    @PostMapping(value = "/settlement/apply", headers = Const.API_VERSION_1_0_0)
    public ViewData apply(@AuthenticationPrincipal OAuth2Authentication oAuth2Authentication,
                          MerchantSettlementApplyRequest request) {
        Long merchantId = ((CustomUserDetails) oAuth2Authentication.getPrincipal()).getId();
        request.setMerchantId(merchantId);
        merchantSettlementService.apply(request);
        return ViewData.builder().message("申请成功").build();
    }

    @GetMapping(value = "/settlement/record", headers = Const.API_VERSION_1_0_0)
    public ViewData record(@AuthenticationPrincipal OAuth2Authentication oAuth2Authentication,
                           MerchantSettlementRecordRequest request) {
        Long merchantId = ((CustomUserDetails) oAuth2Authentication.getPrincipal()).getId();
        request.setMerchantId(merchantId);
        List<MerchantSettlementRecordResponse> response = merchantSettlementService.record(request);
        return ViewData.builder().data(response).message("结算记录").build();
    }

}

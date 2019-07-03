package com.miner.disco.mch.controller;

import com.miner.disco.basic.model.response.ViewData;
import com.miner.disco.mch.consts.Const;
import com.miner.disco.mch.model.request.MerchantBillRequest;
import com.miner.disco.mch.model.request.MerchantOfflineBillRequest;
import com.miner.disco.mch.model.response.MerchantBillResponse;
import com.miner.disco.mch.model.response.MerchantOfflineBillResponse;
import com.miner.disco.mch.oauth.model.CustomUserDetails;
import com.miner.disco.mch.service.MerchantBillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Created by lubycoder@163.com 2019-2-18
 */
@RestController
public class MerchantBillController {

    @Autowired
    private MerchantBillService merchantBillService;

    /**
     * 资金管理--线上预定+线下扫码
     * @param request
     * @return
     */
    @GetMapping(value = "/merchant/bill/list", headers = Const.API_VERSION_1_0_0)
    public ViewData list(@AuthenticationPrincipal OAuth2Authentication oAuth2Authentication,
                         MerchantBillRequest request) {
        Long mchId = ((CustomUserDetails) oAuth2Authentication.getPrincipal()).getId();
        MerchantBillResponse response = merchantBillService.list(mchId, request.getYear(), request.getMonth());
        return ViewData.builder().data(response).message("账单列表").build();
    }

    @GetMapping(value = "/merchant/offline/bill/list", headers = Const.API_VERSION_1_0_0)
    public ViewData offLineBills(@AuthenticationPrincipal OAuth2Authentication oAuth2Authentication,
                                 MerchantOfflineBillRequest request) {
        Long mchId = ((CustomUserDetails) oAuth2Authentication.getPrincipal()).getId();
        request.setMerchantId(mchId);
        List<MerchantOfflineBillResponse> response = merchantBillService.offLineBills(request);
        return ViewData.builder().data(response).message("线下收款账单").build();
    }

}

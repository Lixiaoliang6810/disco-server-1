package com.miner.disco.mch.controller;

import com.miner.disco.basic.model.response.ViewData;
import com.miner.disco.basic.util.Encrypt;
import com.miner.disco.mch.consts.Const;
import com.miner.disco.mch.model.request.MerchantApplyRequest;
import com.miner.disco.mch.model.request.MerchantInfoModifyRequest;
import com.miner.disco.mch.model.request.MerchantRegisterRequest;
import com.miner.disco.mch.model.request.MerchantRestPasswordRequest;
import com.miner.disco.mch.model.response.MerchantDetailsResponse;
import com.miner.disco.mch.model.response.ReceivablesQrcodeResponse;
import com.miner.disco.mch.oauth.model.CustomUserDetails;
import com.miner.disco.mch.service.MerchantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.security.Principal;

/**
 * @author Created by lubycoder@163.com 2019/1/8
 */
@RestController
public class MerchantController {

    @Autowired
    private MerchantService merchantService;

    @PostMapping(value = "/merchant/register", headers = Const.API_VERSION_1_0_0)
    public ViewData register(MerchantRegisterRequest request) {
        Long merchantId = merchantService.register(request);
        return ViewData.builder().data(merchantId).message("注册成功").build();
    }

    @PostMapping(value = "/merchant/apply", headers = Const.API_VERSION_1_0_0)
    public ViewData apply(MerchantApplyRequest request) {
        merchantService.apply(request);
        return ViewData.builder().message("申请成功").build();
    }

    @GetMapping(value = "/merchant/details", headers = Const.API_VERSION_1_0_0)
    public ViewData details(@AuthenticationPrincipal OAuth2Authentication oAuth2Authentication) {
        Long merchantId = ((CustomUserDetails) oAuth2Authentication.getPrincipal()).getId();
        MerchantDetailsResponse response = merchantService.details(merchantId);
        return ViewData.builder().data(response).message("商户详情").build();
    }

    @PostMapping(value = "/merchant/info/modify", headers = Const.API_VERSION_1_0_0)
    public ViewData modify(@AuthenticationPrincipal OAuth2Authentication oAuth2Authentication,
                           MerchantInfoModifyRequest request) {
        Long merchantId = ((CustomUserDetails) oAuth2Authentication.getPrincipal()).getId();
        request.setId(merchantId);
        merchantService.modify(request);
        return ViewData.builder().message("设置成功").build();
    }

    @PostMapping(value = "/merchant/password/reset", headers = Const.API_VERSION_1_0_0)
    public ViewData restPassword(MerchantRestPasswordRequest request) {
        merchantService.resetPassword(request);
        return ViewData.builder().message("重置成功").build();
    }

    @PostMapping(value = "/merchant/receivables/qrcode", headers = Const.API_VERSION_1_0_0)
    public ViewData receivablesQrcode(@AuthenticationPrincipal OAuth2Authentication oAuth2Authentication,
                                      @RequestParam("amount") BigDecimal amount,
                                      @RequestParam(value = "coupon", required = false) String coupon) {
        Long merchantId = ((CustomUserDetails) oAuth2Authentication.getPrincipal()).getId();
        ReceivablesQrcodeResponse response = merchantService.receivablesQrcode(merchantId, amount, coupon);
        return ViewData.builder().data(response).build();
    }

    @GetMapping(value = "/merchant/receivables/status", headers = Const.API_VERSION_1_0_0)
    public ViewData receivablesStatus(@AuthenticationPrincipal OAuth2Authentication oAuth2Authentication,
                                      @RequestParam("key") String key) {
        Long merchantId = ((CustomUserDetails) oAuth2Authentication.getPrincipal()).getId();
        Integer status = merchantService.receivablesStatus(merchantId, key);
        return ViewData.builder().data(status).build();
    }

}

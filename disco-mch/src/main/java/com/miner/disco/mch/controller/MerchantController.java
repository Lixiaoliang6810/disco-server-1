package com.miner.disco.mch.controller;

import com.miner.disco.basic.model.response.ViewData;
import com.miner.disco.mch.consts.Const;
import com.miner.disco.mch.model.request.*;
import com.miner.disco.mch.model.response.CheckReceivablesStatusResponse;
import com.miner.disco.mch.model.response.MerchantDetailsResponse;
import com.miner.disco.mch.oauth.model.CustomUserDetails;
import com.miner.disco.mch.service.MerchantService;
import com.zaki.pay.wx.model.request.ApplyRefundRequest;
import com.zaki.pay.wx.model.response.ApplyRefundResponse;
import com.zaki.pay.wx.model.response.WXQrCodeResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;

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
                                      @RequestParam("winePrice") BigDecimal winePrice,
                                      @RequestParam("foodPrice") BigDecimal foodPrice,
                                      @RequestParam(value = "coupon", required = false) String coupon,
                                      HttpServletRequest servletRequest) throws UnsupportedEncodingException {
        Long merchantId = ((CustomUserDetails) oAuth2Authentication.getPrincipal()).getId();
        ReceivablesQrcodeRequest receivablesQrcodeRequest = new ReceivablesQrcodeRequest();
        receivablesQrcodeRequest.setMerchantId(merchantId);
        receivablesQrcodeRequest.setCoupon(coupon);
        receivablesQrcodeRequest.setWinePrice(winePrice);
        receivablesQrcodeRequest.setFoodPrice(foodPrice);
//        ReceivablesQrcodeResponse response = merchantService.receivablesQrcode(receivablesQrcodeRequest, servletRequest);
        WXQrCodeResponse WXQrCodeResponse = merchantService.unifiedOrder(receivablesQrcodeRequest, servletRequest);
        return ViewData.builder().data(WXQrCodeResponse).build();
    }

    @GetMapping(value = "/merchant/receivables/status", headers = Const.API_VERSION_1_0_0)
    public ViewData receivablesStatus(@AuthenticationPrincipal OAuth2Authentication oAuth2Authentication,
                                      @RequestParam("outTradeNo") String outTradeNo) {
        Long merchantId = ((CustomUserDetails) oAuth2Authentication.getPrincipal()).getId();
        CheckReceivablesStatusResponse checkReceivablesStatusResponse = merchantService.receivablesStatus(merchantId, outTradeNo);
        return ViewData.builder().data(checkReceivablesStatusResponse).build();
    }

    /**
 String outTradeNo;serial_no
String outRefundNo;
 String totalFee;amount
 String refundFee;amount
     */
    @PostMapping(value = "/merchant/refund", headers = Const.API_VERSION_1_0_0)
    public ViewData receivablesStatusx(@AuthenticationPrincipal OAuth2Authentication oAuth2Authentication,
                                       @RequestParam("outTradeNo") String outTradeNo,
                                       @RequestParam("totalFee") String totalFee,
                                       @RequestParam("refundFee") String refundFee){
        ApplyRefundRequest applyRefundRequest = new ApplyRefundRequest();
        applyRefundRequest.setOutTradeNo(outTradeNo);
        applyRefundRequest.setOutRefundNo(outTradeNo);
        applyRefundRequest.setTotalFee(totalFee);
        applyRefundRequest.setRefundFee(refundFee);
        ApplyRefundResponse applyRefundResponse = merchantService.refund(applyRefundRequest);
        if(StringUtils.isNotBlank(applyRefundResponse.getErrCodeDes())){
            return ViewData.builder().data(applyRefundResponse.getErrCodeDes()).build();
        }
        return ViewData.builder().data(applyRefundResponse).build();
    }

}

package com.miner.disco.front.controller;

import com.alipay.api.AlipayApiException;
import com.miner.disco.alipay.support.AlipayService;
import com.miner.disco.alipay.support.model.request.AlipayPreorderRequest;
import com.miner.disco.alipay.support.model.response.AlipayPreorderResponse;
import com.miner.disco.basic.constants.BasicConst;
import com.miner.disco.basic.constants.Environment;
import com.miner.disco.basic.constants.Payment;
import com.miner.disco.basic.model.response.ViewData;
import com.miner.disco.basic.util.ShareCodeUtils;
import com.miner.disco.front.consts.Const;
import com.miner.disco.front.exception.BusinessException;
import com.miner.disco.front.exception.BusinessExceptionCode;
import com.miner.disco.front.oauth.model.CustomUserDetails;
import com.miner.disco.front.service.MerchantReceivablesQrcodeService;
import com.miner.disco.pojo.MerchantReceivablesQrcode;
import com.miner.disco.wxpay.support.WxpayService;
import com.miner.disco.wxpay.support.model.request.WxpayPreorderRequest;
import com.miner.disco.wxpay.support.model.response.WxpayPreorderResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;

/**
 * @author Created by lubycoder@163.com 2019-2-20
 */
@Slf4j
@RestController
public class SweepPaymentController {

    @Autowired
    private AlipayService alipayService;

    @Autowired
    private WxpayService wxpayService;

    @Value("${server.environment}")
    private Environment environment;

    @Value("${server.payment-callback-url}")
    private String paymentCallbackUrl;

    @Autowired
    private MerchantReceivablesQrcodeService merchantReceivablesQrcodeService;

    @PostMapping(value = "/sweep/payment", headers = Const.API_VERSION_1_0_0)
    public ViewData sweepPayment(HttpServletRequest servletRequest,
                                 @RequestParam("key") String key,
                                 @RequestParam("coupon") String coupon,
                                 @RequestParam("payment") Payment payment,
                                 @AuthenticationPrincipal OAuth2Authentication oAuth2Authentication) throws UnsupportedEncodingException {
        Long memberId = ((CustomUserDetails) oAuth2Authentication.getPrincipal()).getId();
        // 查询收款码
        MerchantReceivablesQrcode receivablesQrcode = merchantReceivablesQrcodeService.queryByKey(key);
        if (receivablesQrcode == null) {
            return ViewData.builder().status(BusinessExceptionCode.ILLEGAL_OPERATION.getCode()).message("非法操作").build();
        }
        if (receivablesQrcode.getStatus().intValue() != MerchantReceivablesQrcode.STATUS.WAIT_PAYMENT.getKey()) {
            return ViewData.builder().status(BusinessExceptionCode.ORDERS_REPEATED_PAYMENT.getCode()).message("请勿重复支付").build();
        }
        // 引导员id
        Long vipId = StringUtils.isNotBlank(coupon) ? ShareCodeUtils.codeToId(coupon) : -1;
        String callbackParam = URLEncoder.encode(String.format("qrcodeId=%s&mchId=%s&vipId=%s&memberId=%s&env=%s",
                receivablesQrcode.getId(), receivablesQrcode.getMchId(), vipId, memberId, environment), BasicConst.UTF_8.displayName());
        String orderNo = String.format("%s%s", receivablesQrcode.getId(), receivablesQrcode.getKey());
        switch (payment) {
            case ALIPAY:
                AlipayPreorderResponse appResponse = alipay(servletRequest, orderNo.substring(0, 24), receivablesQrcode.getDiscountPrice(), callbackParam);
                return ViewData.builder().data(appResponse).message("支付宝预支付").build();
            case WXPAY:
                WxpayPreorderResponse response = wxpay(servletRequest, orderNo.substring(0, 11) + System.currentTimeMillis(), receivablesQrcode.getDiscountPrice(), callbackParam);
                return ViewData.builder().data(response).message("微信预支付").build();
            default:
                return ViewData.builder().message("请选择支付方式").build();
        }
    }

    private AlipayPreorderResponse alipay(HttpServletRequest servletRequest, String sn, BigDecimal amount, String callbackParam) throws BusinessException {
        AlipayPreorderRequest request = new AlipayPreorderRequest();
        request.setBody("麦罗佛伦");
        request.setSubject("线下扫码");
        request.setOutTradeNo(sn);
        request.setTotalAmount(String.valueOf(amount));//environment == Environment.RELEASE ? String.valueOf(amount) : "0.01");
        request.setCallbackParam(callbackParam);
        String callbackUrl = (environment == Environment.RELEASE) ? getPath(servletRequest) : paymentCallbackUrl;
        request.setCallbackUrl(String.format("%s%s", callbackUrl, "/alipay/sweep/notify"));
        AlipayPreorderResponse response = null;
        try {
            response = alipayService.appPreorder(request);
        } catch (AlipayApiException e) {
            log.error("call alipay sdk error.", e);
        }
        return response;
    }

    private WxpayPreorderResponse wxpay(HttpServletRequest servletRequest, String sn, BigDecimal amount, String callbackParam) {
        WxpayPreorderRequest wxpayPreorderRequest = new WxpayPreorderRequest();
        wxpayPreorderRequest.setBody("麦罗佛伦");
        wxpayPreorderRequest.setDetail("线上预定");
        wxpayPreorderRequest.setOutTradeNo(sn);
        wxpayPreorderRequest.setCallbackParam(callbackParam);
        wxpayPreorderRequest.setTotalFee(String.valueOf(amount.multiply(BigDecimal.valueOf(100)).toBigInteger()));//environment == Environment.RELEASE ? String.valueOf(amount.multiply(BigDecimal.valueOf(100)).toBigInteger()) : "1");
        String callbackUrl = (environment == Environment.RELEASE) ? getPath(servletRequest) : paymentCallbackUrl;
        wxpayPreorderRequest.setCallbackUrl(String.format("%s%s", callbackUrl, "/wxpay/sweep/notify"));
        return wxpayService.preorder(wxpayPreorderRequest);
    }

    private String getPath(HttpServletRequest request) {
        return String.format("%s://%s:%s", request.getScheme(), request.getServerName(), request.getServerPort());
    }

}

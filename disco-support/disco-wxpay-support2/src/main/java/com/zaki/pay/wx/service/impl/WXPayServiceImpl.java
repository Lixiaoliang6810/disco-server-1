package com.zaki.pay.wx.service.impl;

import com.zaki.pay.wx.constants.WXPayConstants;
import com.zaki.pay.wx.exception.WXPayApiException;
import com.zaki.pay.wx.model.request.WXPayUnifiedOrderRequest;
import com.zaki.pay.wx.model.response.WXPayUnifiedOrderResponse;
import com.zaki.pay.wx.service.WXPayService;
import com.zaki.pay.wx.utils.HttpClientUtil;
import com.zaki.pay.wx.utils.WXPayUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * @author: wz1016_vip@163.com @Date: 2019/7/1
 */
@Slf4j
public class WXPayServiceImpl implements WXPayService {
    /**
     *     app-id: wx1ef5fd586908e6cc
     *     mch-id: 1523752031
     *     api-secret: wXcKfRudjBpVfmcHSNjOpIzn8DCHj5Ov
     */
    private String appId;
    private String mchId;
    private String secret;


    @Override
    public WXPayUnifiedOrderResponse unifiedOrder(WXPayUnifiedOrderRequest request) {
        Map<String, String> requestMap = new HashMap<>();
        requestMap.put("appid", appId);
        requestMap.put("mch_id", mchId);
        requestMap.put("nonce_str", WXPayUtil.generateNonceStr());

        requestMap.put("sign_type", WXPayConstants.MD5);
        requestMap.put("body", request.getBody());
        requestMap.put("detail", request.getDetail());
        requestMap.put("attach", request.getAttach());
        requestMap.put("out_trade_no", request.getOutTradeNo());
        requestMap.put("total_fee", request.getTotalFee());
        requestMap.put("spbill_create_ip", "127.0.0.1");
        requestMap.put("notify_url", request.getNotifyUrl());
        requestMap.put("trade_type", request.getTradeType());

        try {
            String sign = WXPayUtil.generateSignature(requestMap, secret);
            requestMap.put("sign", sign);

            // 请求接口
            String result = HttpClientUtil.doPost(String.format("%s%s", WXPayConstants.DOMAIN_API, WXPayConstants.UNIFIEDORDER_URL_SUFFIX), requestMap);
            log.info("WXPayService unifiedOrder result {}", result.replaceAll("\\n", ""));
            // 接口响应
            Map<String, String> responseMap = WXPayUtil.xmlToMap(result);

            if (responseMap.containsKey("return_code") && StringUtils.equals("FAIL", responseMap.get("return_code"))) {
                System.out.println("微信支付api调用错误>>"+ responseMap.get("err_code") + "::"+ responseMap.get("err_code_des"));
                throw new WXPayApiException("WXPayService unifiedOrder error");
            }

            WXPayUnifiedOrderResponse response = new WXPayUnifiedOrderResponse();
            response.setReturnCode(responseMap.get("return_code"));
            response.setReturnMsg(responseMap.get("return_msg"));

            // 以下字段在return_code为SUCCESS的时候有返回
            response.setAppId(responseMap.get("appid"));
            response.setMchId(responseMap.get("mch_id"));
            response.setNonceStr(responseMap.get("nonce_str"));
            response.setSign(responseMap.get("sign"));
            response.setResultCode(responseMap.get("result_code"));
            /*
             * return_code 和result_code都为SUCCESS的时候有返回
             */
            response.setTradeType(responseMap.get("trade_type"));
            // 预支付交易会话标识
            response.setPrepayId(responseMap.get("prepay_id"));
            // 二维码链接
            response.setCodeUrl(responseMap.get("code_url"));

            return response;
        } catch (Exception e) {
            throw new WXPayApiException("WXPayService unifiedOrder error",e);
        }
    }

    @Override
    public Object complete(Function function) {
        return null;
    }
}

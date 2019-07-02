package com.zaki.pay.wx.service.impl;

import com.zaki.pay.wx.constants.WXPayConstants;
import com.zaki.pay.wx.exception.WXPayApiException;
import com.zaki.pay.wx.model.request.WXPayOrderQueryRequest;
import com.zaki.pay.wx.model.request.WXPayUnifiedOrderRequest;
import com.zaki.pay.wx.model.response.WXPayOrderQueryResponse;
import com.zaki.pay.wx.model.response.WXPayUnifiedOrderResponse;
import com.zaki.pay.wx.service.WXPayService;
import com.zaki.pay.wx.utils.WXPayUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
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
    private String apiSecret;

    public WXPayServiceImpl(String appId, String mchId, String apiSecret) {
        this.appId = appId;
        this.mchId = mchId;
        this.apiSecret = apiSecret;
    }


    @Override
    public String getCodeUrl(WXPayUnifiedOrderRequest request) {
        return unifiedOrder(request).getCodeUrl();
    }

    private String execute(String url,String data) throws IOException {
        HttpPost httpPost = new HttpPost(url);
        StringEntity postEntity = new StringEntity(data, Charset.forName("UTF-8"));
        httpPost.addHeader(HTTP.CONTENT_TYPE, "text/xml");
        httpPost.setEntity(postEntity);

        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpResponse callWeChatResponse = httpClient.execute(httpPost);
        HttpEntity httpEntity = callWeChatResponse.getEntity();
        return EntityUtils.toString(httpEntity, Charset.forName("UTF-8"));
    }
    @Override
    public WXPayUnifiedOrderResponse unifiedOrder(WXPayUnifiedOrderRequest request) throws WXPayApiException {
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
            String sign = WXPayUtil.generateSignature(requestMap, apiSecret);
            requestMap.put("sign", sign);

            String data = WXPayUtil.mapToXml(requestMap);
            String url = String.format("%s%s", WXPayConstants.DOMAIN_API, WXPayConstants.UNIFIEDORDER_URL_SUFFIX);
            // 请求接口
            String result = this.execute(url, data);

            log.info("WXPayService unifiedOrder result {}", result.replaceAll("\\n", ""));
            // 接口响应
            Map<String, String> responseMap = WXPayUtil.xmlToMap(result);

            if (responseMap.containsKey("return_code") && StringUtils.equals("FAIL", responseMap.get("return_code"))) {
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
    @Transactional(readOnly = true)
    public WXPayOrderQueryResponse queryOrder(WXPayOrderQueryRequest request) {
        Map<String, String> requestMap = new HashMap<>();
        requestMap.put("appid", appId);
        requestMap.put("mch_id", mchId);
        requestMap.put("out_trade_no", request.getOutTradeNo());
        requestMap.put("nonce_str", WXPayUtil.generateNonceStr());

        String sign = null;
        try {
            sign = WXPayUtil.generateSignature(requestMap, apiSecret);
            requestMap.put("sign", sign);

            String data = WXPayUtil.mapToXml(requestMap);
            String url = String.format("%s%s", WXPayConstants.DOMAIN_API, WXPayConstants.ORDERQUERY_URL_SUFFIX);
            // 请求接口
            String result = this.execute(url, data);
            log.info("WXPayService queryOrder result {}", result.replaceAll("\\n", ""));
            Map<String, String> responseMap = WXPayUtil.xmlToMap(result);
            if (responseMap.containsKey("return_code") && StringUtils.equals("FAIL", responseMap.get("return_code"))) {
                throw new WXPayApiException("queryOrder error");
            }

            WXPayOrderQueryResponse response = new WXPayOrderQueryResponse();
            response.setReturnCode(responseMap.get("return_code"));
            response.setReturnMsg(responseMap.get("return_msg"));
            // 以下字段在return_code为SUCCESS的时候有返回
            response.setAppId(responseMap.get("appid"));
            response.setMchId(responseMap.get("mch_id"));
            response.setNonceStr(responseMap.get("nonce_str"));
            response.setSign(responseMap.get("sign"));
            response.setResultCode(responseMap.get("result_code"));
            // 以下字段在return_code 、result_code、trade_state都为SUCCESS时有返回
            response.setTradeType(responseMap.get("trade_type"));
            response.setTradeState(responseMap.get("trade_state"));
            response.setBankType(responseMap.get("bank_type"));
            response.setTotalFee(responseMap.get("total_fee"));
            response.setCashFee(responseMap.get("cash_fee"));
            response.setTimeEnd(String.valueOf(WXPayUtil.getCurrentTimestamp()));

            return response;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Object complete(Function function) {
        return null;
    }
}

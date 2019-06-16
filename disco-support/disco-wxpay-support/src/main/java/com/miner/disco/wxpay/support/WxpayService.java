package com.miner.disco.wxpay.support;

import com.miner.disco.wxpay.support.exception.WxpayApiException;
import com.miner.disco.wxpay.support.model.request.WxpayPreorderRequest;
import com.miner.disco.wxpay.support.model.response.WxpayPreorderResponse;
import com.miner.disco.wxpay.support.utils.WXPayConstants;
import com.miner.disco.wxpay.support.utils.WXPayUtil;
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

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * @author Created by lubycoder@163.com 2019/1/10
 */
@Slf4j
public class WxpayService {

    private String appid;
    private String mchid;
    private String secret;

    public WxpayService(String appid, String mchid, String secret) {
        this.appid = appid;
        this.mchid = mchid;
        this.secret = secret;
    }

    public WxpayPreorderResponse preorder(WxpayPreorderRequest request) throws WxpayApiException {
        Map<String, String> wechatParam = new HashMap<>();
        wechatParam.put("appid", appid);
        wechatParam.put("mch_id", mchid);
        wechatParam.put("nonce_str", WXPayUtil.generateNonceStr());
        wechatParam.put("body", request.getBody());
        wechatParam.put("detail", request.getDetail());
        wechatParam.put("total_fee", request.getTotalFee());
        wechatParam.put("spbill_create_ip", "127.0.0.1");
        wechatParam.put("notify_url", request.getCallbackUrl());
        wechatParam.put("out_trade_no", request.getOutTradeNo());
        wechatParam.put("trade_type", "APP");
        wechatParam.put("attach", request.getCallbackParam());
        wechatParam.put("sign_type", WXPayConstants.MD5);
        try {
            String sign = WXPayUtil.generateSignature(wechatParam, secret);
            wechatParam.put("sign", sign);

            String data = WXPayUtil.mapToXml(wechatParam);
            HttpPost httpPost = new HttpPost(String.format("%s%s", WXPayConstants.DOMAIN_API, WXPayConstants.UNIFIEDORDER_URL_SUFFIX));
            StringEntity postEntity = new StringEntity(data, Charset.forName("UTF-8"));
            httpPost.addHeader(HTTP.CONTENT_TYPE, "text/xml");
            httpPost.setEntity(postEntity);

            CloseableHttpClient httpClient = HttpClientBuilder.create().build();
            HttpResponse callWechatResponse = httpClient.execute(httpPost);
            HttpEntity httpEntity = callWechatResponse.getEntity();
            String result = EntityUtils.toString(httpEntity, Charset.forName("UTF-8"));
            log.info("wechat preorder result {}", result.replaceAll("\\n", ""));
            Map<String, String> wechatResultMap = WXPayUtil.xmlToMap(result);
            if (wechatResultMap.containsKey("return_code") && StringUtils.equals("FAIL", wechatResultMap.get("return_code"))) {
                throw new WxpayApiException("preorder order error");
            }
            SortedMap<String, String> resultMap = new TreeMap<>();
            resultMap.put("prepayid", wechatResultMap.get("prepay_id"));
            resultMap.put("appid", appid);
            resultMap.put("partnerid", mchid);
            resultMap.put("noncestr", WXPayUtil.generateNonceStr());
            resultMap.put("timestamp", String.valueOf(WXPayUtil.getCurrentTimestamp()));
            resultMap.put("package", "Sign=WXPay");

            WxpayPreorderResponse response = new WxpayPreorderResponse();
            response.setPrepayid(wechatResultMap.get("prepay_id"));
            response.setAppId(appid);
            response.setTimeStamp(resultMap.get("timestamp"));
            response.setNonceStr(resultMap.get("noncestr"));
            response.setPartnerid(mchid);
            response.setPaySign(WXPayUtil.generateSignature(resultMap, secret));
            return response;
        } catch (Exception e) {
            throw new WxpayApiException("preorder order error", e);
        }
    }

}

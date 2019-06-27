package com.miner.disco.mch.controller;

import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import com.google.common.collect.Maps;
import com.google.gson.JsonObject;
import com.miner.disco.basic.constants.BasicConst;
import com.miner.disco.basic.constants.Payment;
import com.miner.disco.basic.util.JsonParser;
import com.miner.disco.mch.model.request.SweepPaymentNotifyRequest;
import com.miner.disco.mch.service.SweepPaymentService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Created by lubycoder@163.com 2019-06-21
 */
@Slf4j
@RestController
public class SweepPaymentCallbackController {

    @Value("${ali.pay.public-key}")
    private String alipayPublicKey;

    @Value("${ali.pay.sign-type}")
    private String alipaySignType;

    private static final String ALIPAY_NOTIFY_SUCCESS = "SUCCESS";
    private static final String ALIPAY_NOTIFY_FAILURE = "FAILURE";

    private static final String ALIPAY_TRADE_CLOSED = "TRADE_CLOSED";
    private static final String ALIPAY_TRADE_SUCCESS = "TRADE_SUCCESS";

    @Autowired
    private SweepPaymentService sweepPaymentService;

    @PostMapping(value = "/aggregate/alipay/sweep/notify")
    public void sweepAlipayNotify(HttpServletRequest request, HttpServletResponse response) {
        try {
            Map<String, String> params = Maps.newHashMap();
            Map<String, String[]> requestParas = request.getParameterMap();
            for (String k : requestParas.keySet()) {
                String[] values = requestParas.get(k);
                String valuesStr = BasicConst.EMPTY;
                for (int i = 0; i < values.length; i++) {
                    valuesStr = (i == values.length - 1) ? valuesStr + values[i] : valuesStr + values[i] + ",";
                }
                params.put(k, valuesStr);
                if (log.isDebugEnabled()) {
                    log.debug("callback param key {} value {}", k, valuesStr);
                }
            }

            boolean flag = AlipaySignature.rsaCheckV1(params, alipayPublicKey, BasicConst.UTF_8.displayName(), alipaySignType);
            log.info("rsa check flag {}", flag);
            if (!flag) {
                response.getWriter().write(ALIPAY_NOTIFY_FAILURE);
                return;
            }

            String trade_status = params.get("trade_status");
            if (StringUtils.equals(ALIPAY_TRADE_CLOSED, trade_status)) {
                response.getWriter().write(ALIPAY_NOTIFY_SUCCESS);
                return;
            }

            //判断是否是支付成功状态
            if (StringUtils.equals(trade_status, ALIPAY_TRADE_SUCCESS) && StringUtils.isNotBlank(params.get("buyer_pay_amount"))) {
                String notifyId = params.get("notify_id");
                String outTradeNo = params.get("out_trade_no");
                BigDecimal amount = new BigDecimal(params.get("buyer_pay_amount"));
                SweepPaymentNotifyRequest sweepPaymentNotifyRequest = new SweepPaymentNotifyRequest();
                sweepPaymentNotifyRequest.setAmount(amount);
                sweepPaymentNotifyRequest.setNotifyId(notifyId);
                sweepPaymentNotifyRequest.setOutTradeNo(outTradeNo);
                sweepPaymentNotifyRequest.setPayment(Payment.ALIPAY);
                sweepPaymentNotifyRequest.setMetadata(params);
                sweepPaymentService.callback(sweepPaymentNotifyRequest);
                response.getWriter().write(ALIPAY_NOTIFY_SUCCESS);
            }
        } catch (AlipayApiException | IOException e) {
            log.error("notify alipay callback error", e.getMessage(), e);
            try {
                response.getWriter().write(ALIPAY_NOTIFY_FAILURE);
            } catch (IOException e1) {
                log.error("notify alipay callback error", e1);
            }
        }
    }

    private Map<String, String> paramsToMap(String params) throws UnsupportedEncodingException {
        Map<String, String> resultMap = new HashMap<>();
        String[] paramsStr = URLDecoder.decode(params, BasicConst.UTF_8.displayName()).split("&");
        for (String str : paramsStr) {
            String[] temp = str.split("=");
            String k = temp[0];
            String v = temp.length > 1 ? temp[1] : BasicConst.EMPTY;
            resultMap.put(k, v);
        }
        return resultMap;
    }

}

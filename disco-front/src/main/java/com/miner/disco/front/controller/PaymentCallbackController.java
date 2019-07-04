package com.miner.disco.front.controller;

import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import com.google.common.collect.Maps;
import com.miner.disco.basic.constants.BasicConst;
import com.miner.disco.front.model.request.OrdersPaymentNotifyRequest;
import com.miner.disco.front.model.request.OrdersPaymentRequest;
import com.miner.disco.front.service.OrdersService;
import com.miner.disco.wxpay.support.utils.WXPayUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Created by lubycoder@163.com 2019/1/10
 */
@Slf4j
@RestController
public class PaymentCallbackController {

    @Value("${ali.pay.public-key}")
    private String alipayPublicKey;

    @Value("${ali.pay.sign-type}")
    private String alipaySignType;

    @Value("${wechat.pay.api-secret}")
    private String wxpaySecret;

    private static final String ALIPAY_NOTIFY_SUCCESS = "SUCCESS";
    private static final String ALIPAY_NOTIFY_FAILURE = "FAILURE";

    private static final String ALIPAY_TRADE_CLOSED = "TRADE_CLOSED";
    private static final String ALIPAY_TRADE_SUCCESS = "TRADE_SUCCESS";

    private static final String CALL_WECHAT_STATUS_SUCCESS = "SUCCESS";
    private static final String CALL_WECHAT_STATUS_FAILURE = "FAIL";
    private static final String OK = "OK";

    @Autowired
    private OrdersService ordersService;

    @PostMapping(value = "/alipay/orders/notify")
    public void alipayNotify(HttpServletRequest request, HttpServletResponse response) {
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
                String passback_params = URLDecoder.decode(params.get("passback_params"), BasicConst.UTF_8.displayName());
                String notifyId = params.get("notify_id");
                Map<String, String> customParams = paramsToMap(passback_params);
                Long ordersId = Long.parseLong(customParams.get("ordersId"));
                BigDecimal amount = new BigDecimal(params.get("buyer_pay_amount"));
                OrdersPaymentNotifyRequest ordersPaymentNotifyRequest = new OrdersPaymentNotifyRequest();
                ordersPaymentNotifyRequest.setAmount(amount);
                ordersPaymentNotifyRequest.setNotifyId(notifyId);
                ordersPaymentNotifyRequest.setOrdersId(ordersId);
                ordersPaymentNotifyRequest.setPaymentMethod(OrdersPaymentRequest.PAYMENT_METHOD.ALIPAY);
                ordersPaymentNotifyRequest.setMetadata(params);
                ordersService.paymentCallback(ordersPaymentNotifyRequest);
                response.getWriter().write(CALL_WECHAT_STATUS_SUCCESS);
            }
        } catch (AlipayApiException | IOException e) {
            log.error("notify alipay callback error", e.getMessage(), e);
            try {
                response.getWriter().write(CALL_WECHAT_STATUS_FAILURE);
            } catch (IOException e1) {
                log.error("notify alipay callback error", e1);
            }
        }

    }

    @PostMapping(value = "/wxpay/orders/notify")
    public void wxpayNotify(HttpServletRequest request, HttpServletResponse response) {
        try {
            log.info("wechat payment callback.");
            InputStream is = request.getInputStream();
            String result = IOUtils.toString(is);
            log.info("wechat callback info {}", result);
            Map<String, String> params = WXPayUtil.xmlToMap(result);

            String sign = WXPayUtil.generateSignature(params, wxpaySecret);
            log.info("wxpay callback sign {}", sign);
            if (!StringUtils.equals(sign, params.get("sign"))) {
                response.getWriter().write(CALL_WECHAT_STATUS_FAILURE);
                return;
            }

            if (params.get("result_code").equalsIgnoreCase(CALL_WECHAT_STATUS_SUCCESS)) {
                String passback_params = URLDecoder.decode(params.get("attach"), BasicConst.UTF_8.displayName());
                Map<String, String> customParams = paramsToMap(passback_params);
                String notifyId = params.get("transaction_id");
                Long ordersId = Long.parseLong(customParams.get("ordersId"));
                BigDecimal amount = new BigDecimal(params.get("total_fee")).divide(BigDecimal.valueOf(100L), 2, BigDecimal.ROUND_CEILING);

                OrdersPaymentNotifyRequest ordersPaymentNotifyRequest = new OrdersPaymentNotifyRequest();
                ordersPaymentNotifyRequest.setAmount(amount);
                ordersPaymentNotifyRequest.setNotifyId(notifyId);
                ordersPaymentNotifyRequest.setOrdersId(ordersId);
                ordersPaymentNotifyRequest.setPaymentMethod(OrdersPaymentRequest.PAYMENT_METHOD.WXPAY);
                ordersPaymentNotifyRequest.setMetadata(params);
                ordersService.paymentCallback(ordersPaymentNotifyRequest);

                response.getWriter().write(WXPayUtil.setXML(CALL_WECHAT_STATUS_SUCCESS, OK));
            }
        } catch (Exception e) {
            log.error("wechat notify error", e.getMessage(), e);
            try {
                response.getWriter().write(WXPayUtil.setXML(CALL_WECHAT_STATUS_FAILURE, OK));
            } catch (IOException e1) {
                log.error("notify wechat callback error", e);
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

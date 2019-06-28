package com.miner.disco.wxpay.support.model.response;

import lombok.Getter;
import lombok.Setter;

/**
 * @author: wz1016_vip@163.com @Date: 2019/6/28
 */
@Getter
@Setter
public class WxpayAfterOrderResponse {

    private String packageStr = "Sign=WXPay";
    private String appId;
    private String timeStamp;
    private String nonceStr;
    private String paySign;
    private String partnerid;
    private String codeUrl;
    private String returnCode;
    private String sign;
    private String tradeType;
    private String tradeState;
    private String bankType;
    private String totalFee;
    private String cashFee;
    private String transactionId;
}

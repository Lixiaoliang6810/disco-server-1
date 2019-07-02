package com.miner.disco.wxpay.support.model.request;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * Created by lubycoder@163.com on 2018/4/5.
 */
@Getter
@Setter
public class WxPayPreOrderRequest implements Serializable {

    private String body;
    private String detail;
    private String totalFee;
    private String outTradeNo;
    private String callbackUrl;
    private String callbackParam;
    private String tradeType = "NATIVE";

}

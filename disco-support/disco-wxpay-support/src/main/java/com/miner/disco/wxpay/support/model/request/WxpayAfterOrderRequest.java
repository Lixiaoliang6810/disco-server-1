package com.miner.disco.wxpay.support.model.request;

import lombok.Getter;
import lombok.Setter;

/**
 * @author: wz1016_vip@163.com @Date: 2019/6/28
 */
@Getter
@Setter
public class WxPayAfterOrderRequest {
    private String appid;
    private String mchid;
    private String outTradeNo;
    private String nonceStr;
    private String sign;
}

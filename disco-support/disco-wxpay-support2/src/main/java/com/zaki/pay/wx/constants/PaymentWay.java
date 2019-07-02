package com.zaki.pay.wx.constants;

/**
 * @author Created by lubycoder@163.com 2019-2-20
 */
public enum PaymentWay implements BasicEnum {

    ALIPAY(1, "支付宝支付"),
    WXPAY(2, "微信支付"),
    WAP_ALIPAY(3, "支付宝网页支付"),
    WAP_WXPAY(4, "微信网页支付");

    Integer key;
    String value;

    PaymentWay(Integer key, String value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public Integer getKey() {
        return this.key;
    }

    @Override
    public String getValue() {
        return this.value;
    }

}

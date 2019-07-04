package com.zaki.pay.wx.constants;

/**
 * @author: wz1016_vip@163.com  2019/7/2
 */
public enum WXOrderStatus implements BasicEnum{

    WAIT_PAYMENT(1, "等待支付"),

    PAY_SUCCESS(2, "支付成功"),

    PAY_OVERTIME(3, "支付超时"),

    PAY_ERROR(4, "支付异常"),

    APPLY_REFUND(5, "申请退款"),

    REFUND_SUCCESS(6, "退款成功"),

    REFUND_OVERTIME(7, "退款超时"),

    REFUND_ERROR(8, "退款异常"),

    ORDER_CLOSED(9, "订单关闭"),

    ;

    Integer key;
    String value;

    @Override
    public Integer getKey() {
        return this.key;
    }

    @Override
    public String getValue() {
        return this.value;
    }

    WXOrderStatus(Integer key, String value) {
        this.key = key;
        this.value = value;
    }

}

package com.zaki.pay.wx.model.request;

import lombok.Getter;
import lombok.Setter;

/**
 * @author: wz1016_vip@163.com @Date: 2019/7/2
 */
@Getter
@Setter
public class ApplyRefundRequest extends BaseRequest{
    /**
     * 商户订单号-
     */
    private String outTradeNo;
    /**
     * 商户退款单号
     */
    private String outRefundNo;
    /**
     * 订单金额
     */
    private String totalFee;
    /**
     * 退款金额
     */
    private String refundFee;

    /**
     * 微信订单号--outTradeNo/transactionId 二选一
     */
    private String transactionId;
    /**
     * 退款原因
     */
    private String refundDesc;
    /**
     * 退款资金来源
     */
    private String refundAccount;
    /**
     * 退款结果通知url
     */
    private String notifyUrl;
}

package com.zaki.pay.wx.model.response;

import lombok.Getter;
import lombok.Setter;

/**
 * @author: wz1016_vip@163.com @Date: 2019/7/2
 */
@Getter
@Setter
public class ApplyRefundResponse extends BaseResponse{
    /**
     * 业务结果
     */
    private String resultCode;
    /**
     * 商户订单号-
     */
    private String outTradeNo;
    /**
     * 微信订单号
     */
    private String transactionId;
    /**
     * 商户退款单号
     */
    private String outRefundNo;
    /**
     * 微信退款单号
     */
    private String refundId;
    /**
     * 退款金额
     */
    private String refundFee;
    /**
     * 标价金额
     */
    private String totalFee;
    /**
     * 现金支付金额
     */
    private String cashFee;
    /**
     * 错误消息描述
     */
    private String errCodeDes;
}

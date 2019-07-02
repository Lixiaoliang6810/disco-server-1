package com.zaki.pay.wx.model.response;

import lombok.Getter;
import lombok.Setter;

/**
 * @author: wz1016_vip@163.com @Date: 2019/7/2
 */
@Getter
@Setter
public class WXPayOrderQueryResponse extends BaseResponse{
    /*
     * 以下字段在return_code为SUCCESS的时候有返回
     */
    /**
     * 公众账号ID
     */
    private String appId;
    /**
     * 商户号
     */
    private String mchId;
    /**
     * 随机字符串
     */
    private String nonceStr;
    /**
     * 签名
     */
    private String sign;
    /**
     * 业务结果
     */
    private String resultCode;

    /*
     * 以下字段在return_code 、result_code、trade_state都为SUCCESS时有返回 ，
     * 如trade_state不为 SUCCESS，则只返回out_trade_no（必传）和attach（选传）
     */
    /**
     * 用户标识
     */
    private String openId;
    /**
     * 是否关注公众账号
     */
    private String isSubscribe;
    /**
     * 交易类型
     */
    private String tradeType;
    /**
     * 交易状态
     */
    private String tradeState;
    /**
     * 付款银行
     */
    private String bankType;
    /**
     * 标价金额
     */
    private String totalFee;
    /**
     * 现金支付金额
     */
    private String cashFee;
    /**
     * 微信支付订单号
     */
    private String transactionId;
    /**
     * 商户订单号
     */
    private String outTradeNo;
    /**
     * 现金支付金额
     */
    private String timeEnd;
    /**
     * 支付完成时间
     */
    private String tradeStateDesc;



}

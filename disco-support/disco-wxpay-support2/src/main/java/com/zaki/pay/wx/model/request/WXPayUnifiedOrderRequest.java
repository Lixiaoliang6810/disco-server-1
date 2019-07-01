package com.zaki.pay.wx.model.request;

import lombok.Getter;
import lombok.Setter;

/**
 * 统一下单请求参数
 *
 * @author: wz1016_vip@163.com @Date: 2019/7/1
 * {@link:https://pay.weixin.qq.com/wiki/doc/api/native.php?chapter=9_1}
 */
@Getter
@Setter
public class WXPayUnifiedOrderRequest {
    /*
     * 以下是必填参数--非驼峰命名，方便对应
     */
    /**
     * 公众账号ID
     */
    private String appid;
    /**
     * 商户号
     */
    private String mch_id;
    /**
     * 随机字符串
     */
    private String nonce_str;
    /**
     * 签名
     */
    private String sign;
    /**
     * 商品描述
     */
    private String body;
    /**
     * 商户订单号
     */
    private String out_trade_no;
    /**
     * 标价金额
     */
    private String total_fee;
    /**
     * 终端IP
     */
    private String spbill_create_ip;
    /**
     * 通知地址
     */
    private String notify_url;
    /**
     * 交易类型
     */
    private String trade_type = TRADE_TYPE.NATIVE.name();

    public enum TRADE_TYPE{
        JSAPI,
        NATIVE,
        APP,
        MWEB
    }
}

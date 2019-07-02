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
     * 商品描述-
     */
    private String body;
    /**
     * 商品详情
     */
    private String detail;
    /**
     * 附加数据
     */
    private String attach;
    /**
     * 商户订单号-
     */
    private String outTradeNo;
    /**
     * 标价金额-
     */
    private String totalFee;
    /**
     * 终端IP
     */
    private String spbillCreateIp;
    /**
     * 异步接收微信支付结果通知的回调地址-
     * 通知url必须为外网可访问的url，不能携带参数
     */
    private String notifyUrl;
    /**
     * 交易类型--默认扫码支付
     */
    private String tradeType = TRADE_TYPE.NATIVE.name();

    public enum TRADE_TYPE{
        JSAPI,
        NATIVE,
        APP,
        MWEB
    }
}

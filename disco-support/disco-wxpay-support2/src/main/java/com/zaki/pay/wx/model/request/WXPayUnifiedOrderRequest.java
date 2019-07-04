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
public class WXPayUnifiedOrderRequest extends BaseRequest{
    /**
     * 商品描述-
     */
    private String body;
    /**
     * 商户订单号-
     */
    private String outTradeNo;
    /**
     * 标价金额-
     */
    private String totalFee;
    /**
     * 交易类型--默认扫码支付
     */
    private String tradeType = TRADE_TYPE.NATIVE.name();

    /**
     * 商品详情
     */
    private String detail;
    /**
     * 附加数据
     */
    private String attach;
    /**
     * 终端IP
     */
    private String spbillCreateIp;
    /**
     * 异步接收微信支付结果通知的回调地址-
     * 通知url必须为外网可访问的url，不能携带参数
     */
    private String notifyUrl;

    public enum TRADE_TYPE{
        JSAPI,
        NATIVE,
        APP,
        MWEB
    }
}

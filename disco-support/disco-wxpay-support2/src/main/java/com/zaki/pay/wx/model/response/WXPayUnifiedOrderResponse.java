package com.zaki.pay.wx.model.response;

import lombok.Getter;
import lombok.Setter;

/**
 * @author: wz1016_vip@163.com @Date: 2019/7/1
 */
@Getter
@Setter
public class WXPayUnifiedOrderResponse {
    /**
     * 返回状态码
     */
    private String return_code;
    /**
     * 返回信息
     */
    private String return_msg;
    /*
     * 以下字段在return_code为SUCCESS的时候有返回
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
     * 业务结果
     */
    private String result_code;

    /*
     * 以下字段在return_code 和result_code都为SUCCESS的时候有返回
     */
    /**
     * 交易类型
     */
    private String trade_type;
    /**
     * 预支付交易会话标识--有效期为2小时
     */
    private String prepay_id;

    /**
     * 二维码链接--trade_type=NATIVE时有返回
     */
    private String code_url;


}

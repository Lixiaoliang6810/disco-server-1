package com.zaki.pay.wx.model.response;

import lombok.Getter;
import lombok.Setter;

/**
 * @author: wz1016_vip@163.com @Date: 2019/7/2
 */
@Getter
@Setter
public class BaseResponse {
    /**
     * 返回状态码
     */
    private String returnCode;
    /**
     * 返回信息
     */
    private String returnMsg;
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

}

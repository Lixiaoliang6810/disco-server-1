package com.zaki.pay.wx.model.response;

import lombok.Getter;
import lombok.Setter;

/**
 * @author: wz1016_vip@163.com @Date: 2019/7/1
 */
@Getter
@Setter
public class WXPayUnifiedOrderResponse extends BaseResponse{
    /*
     * 以下字段在return_code为SUCCESS的时候有返回
     */
    /**
     * 业务结果
     */
    private String resultCode;
    /*
     * 以下字段在return_code 和result_code都为SUCCESS的时候有返回
     */
    /**
     * 交易类型
     */
    private String tradeType;
    /**
     * 预支付交易会话标识--有效期为2小时
     */
    private String prepayId;
    /**
     * 二维码链接--trade_type=NATIVE时有返回
     */
    private String codeUrl;


}

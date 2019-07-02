package com.zaki.pay.wx.model.request;

import lombok.Getter;
import lombok.Setter;

/**
 * @author: wz1016_vip@163.com @Date: 2019/7/2
 */
@Getter
@Setter
public class WXPayOrderQueryRequest extends BaseRequest{
    /**
     * 微信订单号--outTradeNo/transactionId 二选一
     */
    private String transactionId;


}

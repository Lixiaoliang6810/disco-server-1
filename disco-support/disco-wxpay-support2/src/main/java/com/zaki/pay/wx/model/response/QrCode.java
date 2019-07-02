package com.zaki.pay.wx.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.beans.ConstructorProperties;
import java.math.BigDecimal;

/**
 * @author: wz1016_vip@163.com @Date: 2019/7/2
 */
@Getter
@Setter
public class QrCode {
    /**
     * 二维码
     */
    private String qrcode;
    /**
     * 流水号
     */
    private String outTradeNo;
    /**
     * 订单总金额
     */
    private String totalFee;
    /**
     * 原价
     */
    private BigDecimal originalPrice;
    /**
     * 折后价
     */
    private BigDecimal discountPrice;
}

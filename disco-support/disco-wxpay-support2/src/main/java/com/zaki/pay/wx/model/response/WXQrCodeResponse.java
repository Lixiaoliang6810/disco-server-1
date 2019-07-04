package com.zaki.pay.wx.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.beans.ConstructorProperties;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author: wz1016_vip@163.com @Date: 2019/7/2
 */
@Getter
@Setter
public class WXQrCodeResponse implements Serializable {
    private static final long serialVersionUID = 6618476693021456958L;

    /**
     * 二维码
     */
    private String qrcode;
    /**
     * 流水号
     */
    private String outTradeNo;
    /**
     * 原价
     */
    private String originalPrice;
    /**
     * 折后价
     */
    private String discountPrice;
}

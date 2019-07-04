package com.zaki.pay.wx.model.request;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author: wz1016_vip@163.com  2019/7/4
 */
@Getter
@Setter
public class WXQrCodeRequest implements Serializable {
    private static final long serialVersionUID = -5316033559252761349L;

    /**
     * 商户id
     */
    private Long merchantId;
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

package com.zaki.pay.wx.model.request;

import lombok.Getter;
import lombok.Setter;

/**
 * @author: wz1016_vip@163.com  2019/7/2
 */
@Getter
@Setter
public class QrCodeRequest {
    private String code_url;
    private String total_fee;
    private String out_trade_no;
}

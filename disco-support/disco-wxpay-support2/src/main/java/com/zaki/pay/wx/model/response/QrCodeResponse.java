package com.zaki.pay.wx.model.response;

import lombok.Getter;
import lombok.Setter;

/**
 * @author: wz1016_vip@163.com  2019/7/2
 */
@Getter
@Setter
public class QrCodeResponse {
    private String code_url;
    private String total_fee;
    private String out_trade_no;
}

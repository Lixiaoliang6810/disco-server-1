package com.zaki.pay.wx.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 二维码对象
 * @author: wz1016_vip@163.com @Date: 2019/7/1
 */
@Getter
@Setter
public class QrCode implements Serializable {
    private static final long serialVersionUID = -6717433712898210882L;

    private String code_url;
    private String total_fee;
    private String out_trade_no;
    
}

package com.zaki.pay.wx.service;

import com.zaki.pay.wx.model.dto.QrCode;

/**
 * @author: wz1016_vip@163.com @Date: 2019/7/1
 */
public interface WXPayService {

    QrCode genQrCode();
}

package com.zaki.pay.wx.service;

import com.zaki.pay.wx.model.dto.QrCode;
import com.zaki.pay.wx.model.request.QrCodeRequest;
import com.zaki.pay.wx.model.request.WXPayUnifiedOrderRequest;
import com.zaki.pay.wx.model.response.QrCodeResponse;
import com.zaki.pay.wx.model.response.WXPayUnifiedOrderResponse;

/**
 * @author: wz1016_vip@163.com @Date: 2019/7/1
 */
public interface WXPayService {

    /**
     * 生成二维码
     * @return 二维码对象
     */
    QrCodeResponse genQrCode(QrCodeRequest request);

    /**
     * 统一下单
     * @param request 请求参数体
     * @return 返回参数体
     */
    WXPayUnifiedOrderResponse d(WXPayUnifiedOrderRequest request);

}

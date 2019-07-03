package com.zaki.pay.wx.service;

import com.zaki.pay.wx.model.request.ApplyRefundRequest;
import com.zaki.pay.wx.model.request.WXPayOrderQueryRequest;
import com.zaki.pay.wx.model.request.WXPayUnifiedOrderRequest;
import com.zaki.pay.wx.model.response.ApplyRefundResponse;
import com.zaki.pay.wx.model.response.WXPayOrderQueryResponse;
import com.zaki.pay.wx.model.response.WXPayUnifiedOrderResponse;

import java.util.function.Function;

/**
 * @author: wz1016_vip@163.com @Date: 2019/7/1
 */
public interface WXPayService<T,R> {

    String getCodeUrl(WXPayUnifiedOrderRequest request);
    /**
     * 统一下单--通过响应的 code_url 生成二维码
     * @param request 请求参数体
     * @return 返回参数体
     */
    WXPayUnifiedOrderResponse unifiedOrder(WXPayUnifiedOrderRequest request);

    /**
     * 订单查询
     * @param request
     * @return 查询响应结果
     */
    WXPayOrderQueryResponse queryOrder(WXPayOrderQueryRequest request);

    /**
     * 申请退款
     * @param request
     * @return 退款响应体
     */
    ApplyRefundResponse applyRefund(ApplyRefundRequest request);




    /**
     * 接口调用之后的业务逻辑
     */
    R complete(Function<T,R> function);



}

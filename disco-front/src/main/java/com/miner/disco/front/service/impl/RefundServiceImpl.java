package com.miner.disco.front.service.impl;

import com.miner.disco.alipay.support.AlipayService;
import com.miner.disco.front.dao.*;
import com.miner.disco.front.service.OrdersService;
import com.miner.disco.front.service.RefundService;
import com.zaki.pay.wx.service.WXPayService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author: wz1016_vip@163.com  2019/7/3
 */
public class RefundServiceImpl implements RefundService {

    private final WXPayService wxPayService;
    @Autowired
    public RefundServiceImpl(WXPayService wxPayService){
        this.wxPayService = wxPayService;
    }

    @Autowired
    private OrdersMapper ordersMapper;

    @Autowired
    private MerchantGoodsMapper merchantGoodsMapper;

    @Autowired
    private AlipayService alipayService;

    @Autowired
    private MerchantBillMapper merchantBillMapper;

    @Autowired
    private MemberBillMapper memberBillMapper;

    @Autowired
    private MerchantMapper merchantMapper;

    @Override
    public void refund(String outTradeNo) {

    }

    /**
     * 退款逻辑：
     *     1.订单未消费--极速退款，一般是定金
     *     2.订单已消费：
     *     用户申请退款-》店家可看到消费详情，根据订单流水号查询-》店家同意-》退款
     */
}

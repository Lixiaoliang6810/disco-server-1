package com.miner.disco.mch.service.impl;

import com.miner.disco.mch.service.WXOrdersService;
import com.zaki.pay.wx.service.WXPayService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author: wz1016_vip@163.com  2019/7/3
 */
public class WXOrdersServiceImpl implements WXOrdersService {

    private final WXPayService wxPayService;

    @Autowired
    public WXOrdersServiceImpl(WXPayService wxPayService){
        this.wxPayService = wxPayService;
    }


    public void refund(){
    }

    /**
     * 退款逻辑：
     *     1.订单未消费--极速退款，一般是定金
     *     2.订单已消费：
     *     用户申请退款-》店家可看到消费详情，根据订单流水号查询-》店家同意-》退款
     */
}

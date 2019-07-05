package com.miner.disco.mch.service.impl;

import com.miner.disco.basic.assertion.Assert;
import com.miner.disco.basic.util.JsonParser;
import com.miner.disco.basic.util.ShareCodeUtils;
import com.miner.disco.basic.util.UidMaskUtils;
import com.miner.disco.mch.dao.*;
import com.miner.disco.mch.exception.MchBusinessException;
import com.miner.disco.mch.exception.MchBusinessExceptionCode;
import com.miner.disco.mch.model.request.SweepPaymentNotifyRequest;
import com.miner.disco.mch.service.SweepPaymentService;
import com.miner.disco.pojo.*;
import com.zaki.pay.wx.model.response.WXPayOrderQueryResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author Created by lubycoder@163.com 2019-06-21
 */
@Slf4j
@Service
public class SweepPaymentServiceImpl implements SweepPaymentService {

    @Autowired
    private CallbackNotifyMapper callbackNotifyMapper;

    @Autowired
    private MerchantAggregateQrcodeMapper merchantAggregateQrcodeMapper;

    @Autowired
    private MerchantMapper merchantMapper;

    @Autowired
    private MerchantBillMapper merchantBillMapper;

    @Autowired
    private MemberBillMapper memberBillMapper;

    @Autowired
    private MemberMapper memberMapper;

    @Autowired
    private PlatformBillMapper platformBillMapper;

    @Autowired
    private MerchantGoodsMapper merchantGoodsMapper;

    @Autowired
    private OrdersMapper ordersMapper;


    @Override
    public void callback(SweepPaymentNotifyRequest request) throws MchBusinessException {
        CallbackNotify callbackNotify = callbackNotifyMapper.queryBySnAndType(request.getNotifyId(), request.getPayment().getKey());
        if (callbackNotify != null) {
            log.info("{} sn {} repeated callbacks", request.getPayment().getValue(), request.getNotifyId());
            return;
        }
        MerchantAggregateQrcode merchantAggregateQrcode = merchantAggregateQrcodeMapper.queryByOutTradeNo(request.getOutTradeNo());
        if (merchantAggregateQrcode == null) {
            log.error("illegal callbacks, metadata `{}`", JsonParser.serializeToJson(request.getMetadata()));
            return;
        }

        //记录回调记录
        callbackNotify = new CallbackNotify();
        callbackNotify.setCallbackSn(request.getNotifyId());
        callbackNotify.setCallbackType(request.getPayment().getKey());
        callbackNotify.setMetadata(JsonParser.serializeToJson(request.getMetadata()));
        callbackNotify.setCreateDate(new Date());
        callbackNotify.setUpdateDate(new Date());
        callbackNotifyMapper.insert(callbackNotify);

        MerchantAggregateQrcode saveMerchantAggregateQrcode = new MerchantAggregateQrcode();
        saveMerchantAggregateQrcode.setStatus(MerchantReceivablesQrcode.STATUS.PAY_SUCCESS.getKey());
        saveMerchantAggregateQrcode.setId(merchantAggregateQrcode.getId());
        saveMerchantAggregateQrcode.setPaymentDate(new Date());
        saveMerchantAggregateQrcode.setUpdateDate(new Date());
        merchantAggregateQrcodeMapper.updateByPrimaryKey(saveMerchantAggregateQrcode);

        Merchant merchant = merchantMapper.queryByPrimaryKeyForUpdate(merchantAggregateQrcode.getMchId());
        BigDecimal merchantAmount = request.getAmount();
        //记录引导员抽成
        if (!StringUtils.isBlank(merchantAggregateQrcode.getCoupon())) {
            Long vipId = ShareCodeUtils.codeToId(merchantAggregateQrcode.getCoupon());
            BigDecimal vipAmount = merchantAggregateQrcode.getFoodPrice().multiply(merchant.getVipRatio());
            merchantAmount = merchantAmount.subtract(vipAmount);

            //记录VIP引导员流水
            MemberBill memberBill = new MemberBill();
            String sno = String.format("%s%s%s",
                    UidMaskUtils.idToCode(merchant.getId()),
                    UidMaskUtils.idToCode(vipId),
                    System.currentTimeMillis());
            memberBill.setSerialNo(sno);
            memberBill.setAmount(vipAmount);
            memberBill.setUserId(vipId);
            memberBill.setType(MemberBill.TYPE.IN.getKey());
            memberBill.setTradeType(MemberBill.TRADE_TYPE.GUIDE_ROYALTY.getKey());
            memberBill.setRemark(MemberBill.TRADE_TYPE.GUIDE_ROYALTY.getValue());
            memberBill.setSource(MemberBill.TRADE_TYPE.GUIDE_ROYALTY.getValue());
            memberBill.setSourceId(merchantAggregateQrcode.getId());
            memberBill.setCreateDate(new Date());
            memberBill.setUpdateDate(new Date());
            memberBillMapper.insert(memberBill);

            //更新VIP引导员余额
            Member member = memberMapper.queryByPrimaryKeyForUpdate(vipId);
            Member saveMember = new Member();
            saveMember.setId(vipId);
            saveMember.setBalance(member.getBalance().add(vipAmount));
            memberMapper.updateByPrimaryKey(saveMember);
        }

        //记录平台抽成
        BigDecimal platformAmount = merchantAggregateQrcode.getFoodPrice().multiply(merchant.getPlatformRatio());
        merchantAmount = merchantAmount.subtract(platformAmount);

        PlatformBill platformBill = new PlatformBill();
        platformBill.setAmount(platformAmount);
        platformBill.setType(PlatformBill.TYPE.IN.getKey());
        platformBill.setTradeType(PlatformBill.TRADE_TYPE.SWEEP_PAYMENT_TAKE.getKey());
        platformBill.setCreateDate(new Date());
        platformBill.setUpdateDate(new Date());
        platformBillMapper.insert(platformBill);

        //记录商户流水
        MerchantBill merchantBill = new MerchantBill();
        merchantBill.setAmount(merchantAmount);
        merchantBill.setMerchantId(merchantAggregateQrcode.getMchId());
        merchantBill.setSourceId(merchantAggregateQrcode.getId());
        merchantBill.setCreateDate(new Date());
        merchantBill.setUpdateDate(new Date());
        merchantBill.setType(MerchantBill.STATUS.IN.getKey());
        merchantBill.setTradeType(MerchantBill.TRADE_STATUS.OFF_LINE.getKey());
        merchantBill.setRemark(MerchantBill.TRADE_STATUS.OFF_LINE.getValue());
        merchantBillMapper.insert(merchantBill);

        //更新商户余额
        Merchant saveMerchant = new Merchant();
        saveMerchant.setId(merchant.getId());
        saveMerchant.setUsableBalance(merchant.getUsableBalance().add(merchantAmount));
        merchantMapper.updateByPrimaryKey(saveMerchant);

    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void doUpdateBizAsync(String outTradeNo) {
        // 更新收款码状态为2 >>>>>>>>>
        MerchantAggregateQrcode aggregateQrcode = merchantAggregateQrcodeMapper.queryByOutTradeNo(outTradeNo);
        MerchantAggregateQrcode merchantAggregateQrcode = new MerchantAggregateQrcode();
        if(aggregateQrcode!=null){
            BeanUtils.copyProperties(aggregateQrcode,merchantAggregateQrcode);
            merchantAggregateQrcode.setStatus(MerchantAggregateQrcode.STATUS.PAY_SUCCESS.getKey());
            merchantAggregateQrcode.setPaymentDate(new Date());
            merchantAggregateQrcodeMapper.updateByPrimaryKey(merchantAggregateQrcode);
        }

        //更新订单信息--支付成功 >>>>>>>>>
        Orders orders = ordersMapper.queryByOutTradeNo(outTradeNo);

        Orders saveOrders = new Orders();
        BeanUtils.copyProperties(orders,saveOrders);
        // 支付成功后将姓名改成微信用户id-- openid
        saveOrders.setBuyer(-1L);
        saveOrders.setFullname("微信线下扫码用户-已付款");
        saveOrders.setUpdateDate(new Date());
        saveOrders.setPaymentDate(new Date());
        saveOrders.setStatus(Orders.STATUS.COMPLETE.getKey());
        ordersMapper.updateByPrimaryKey(saveOrders);

        //更新商品信息 >>>>>>>>>
        MerchantGoods merchantGoods = merchantGoodsMapper.queryByPrimaryKey(orders.getGoodsId());
        if(merchantGoods!=null){
            MerchantGoods saveMerchantGoods = new MerchantGoods();
            saveMerchantGoods.setId(orders.getGoodsId());
            saveMerchantGoods.setSalesVolume(merchantGoods.getSalesVolume() == null ? 1 : merchantGoods.getSalesVolume() + 1);
            merchantGoodsMapper.updateByPrimaryKey(saveMerchantGoods);
        }

        //更新商户余额 >>>>>>>>>
        Merchant merchant = merchantMapper.queryByPrimaryKeyForUpdate(orders.getSeller());
        Assert.notNull(merchant, MchBusinessExceptionCode.OBJECT_DOES_NOT_EXIST.getCode(), "商户不存在");
        Merchant saveMerchant = new Merchant();
        saveMerchant.setId(merchant.getId());
        saveMerchant.setFrozenBalance(merchant.getFrozenBalance().add(orders.getTotalMoney()));
        saveMerchant.setUpdateDate(new Date());
        merchantMapper.updateByPrimaryKey(saveMerchant);

        //记录回调记录 >>>>>>>>>
//        CallbackNotify callbackNotify = callbackNotifyMapper.queryBySnAndType(request.getNotifyId(), request.getPaymentMethod().getKey());
//        if (callbackNotify != null) {
//            log.info("{} sn {} repeated callbacks", request.getPaymentMethod().getValue(), request.getNotifyId());
//            return;
//        }
//        CallbackNotify callbackNotify = new CallbackNotify();
////        callbackNotify.setCallbackSn(request.getNotifyId());
//        callbackNotify.setCallbackSn(outTradeNo);
////        callbackNotify.setCallbackType(request.getPaymentMethod().getKey());
//        assert aggregateQrcode != null;
//        callbackNotify.setCallbackType(aggregateQrcode.getPaymentWay());
//        callbackNotify.setMetadata(aggregateQrcode.getMetadata());
////        callbackNotify.setMetadata(JsonParser.serializeToJson(request.getMetadata()));
//        callbackNotify.setCreateDate(new Date());
//        callbackNotify.setUpdateDate(new Date());
//        callbackNotifyMapper.insert(callbackNotify);

        // 线下扫码用户没有个人流水
//                //记录用户流水 >>>>>>>>>
//                MemberBill memberBill = new MemberBill();
//                memberBill.setSerialNo(outTradeNo);
//                memberBill.setAmount(orders.getTailMoney());
//                memberBill.setUserId(orders.getBuyer());
//                memberBill.setType(MemberBill.TYPE.OUT.getKey());
//                memberBill.setTradeType(MemberBill.TRADE_TYPE.ONLINE_PURCHASE.getKey());
//                memberBill.setRemark(MemberBill.TRADE_TYPE.ONLINE_PURCHASE.getValue());
//                memberBill.setSource(merchantGoods.getName());
//                memberBill.setSourceId(orders.getId());
//                memberBill.setCreateDate(new Date());
//                memberBill.setUpdateDate(new Date());
//                memberBillMapper.insert(memberBill);

        //记录商户流水 >>>>>>>>>
        MerchantBill merchantBill = new MerchantBill();
        merchantBill.setAmount(orders.getTotalMoney());
        merchantBill.setMerchantId(orders.getSeller());
        // 商户该条流水来源
        merchantBill.setSourceId(orders.getId());
        merchantBill.setCreateDate(new Date());
        merchantBill.setUpdateDate(new Date());
        // 类型-- 1:收入
        merchantBill.setType(MerchantBill.STATUS.IN.getKey());
        // 交易类型-- 2:线下扫码
        merchantBill.setTradeType(MerchantBill.TRADE_STATUS.OFF_LINE.getKey());
        merchantBill.setRemark(MerchantBill.TRADE_STATUS.OFF_LINE.getValue());
        merchantBillMapper.insert(merchantBill);
        // -------------------<<<<<<<<<<<<<----------------------
    }
    /**
     * 异步执行支付成功后的业务
     *
     * @param response
     * @param outTradeNo
     */
//    private void doUpdateBizAsync(WXPayOrderQueryResponse response, String outTradeNo){
//        // 更新收款码状态为2 >>>>>>>>>
//        MerchantAggregateQrcode aggregateQrcode = merchantAggregateQrcodeMapper.queryByOutTradeNo(outTradeNo);
//        MerchantAggregateQrcode merchantAggregateQrcode = new MerchantAggregateQrcode();
//        if(aggregateQrcode!=null){
//            BeanUtils.copyProperties(aggregateQrcode,merchantAggregateQrcode);
//            merchantAggregateQrcode.setStatus(MerchantAggregateQrcode.STATUS.PAY_SUCCESS.getKey());
//            merchantAggregateQrcode.setPaymentDate(new Date());
//            merchantAggregateQrcodeMapper.updateByPrimaryKey(merchantAggregateQrcode);
//        }
//
//        //更新订单信息--支付成功 >>>>>>>>>
//        Orders orders = ordersMapper.queryByOutTradeNo(outTradeNo);
//
//        Orders saveOrders = new Orders();
//        BeanUtils.copyProperties(orders,saveOrders);
//        // 支付成功后将姓名改成微信用户id-- openid
//        saveOrders.setBuyer(-1L);
//        if(null != response && StringUtils.isBlank(response.getOpenId())){
//            saveOrders.setFullname(response.getOpenId());
//        }else {
//            saveOrders.setFullname("微信线下扫码用户-已付款");
//        }
//        saveOrders.setUpdateDate(new Date());
//        saveOrders.setPaymentDate(new Date());
//        saveOrders.setStatus(Orders.STATUS.COMPLETE.getKey());
//        ordersMapper.updateByPrimaryKey(saveOrders);
//
//        //更新商品信息 >>>>>>>>>
//        MerchantGoods merchantGoods = merchantGoodsMapper.queryByPrimaryKey(orders.getGoodsId());
//        if(merchantGoods!=null){
//            MerchantGoods saveMerchantGoods = new MerchantGoods();
//            saveMerchantGoods.setId(orders.getGoodsId());
//            saveMerchantGoods.setSalesVolume(merchantGoods.getSalesVolume() == null ? 1 : merchantGoods.getSalesVolume() + 1);
//            merchantGoodsMapper.updateByPrimaryKey(saveMerchantGoods);
//        }
//
//        //更新商户余额 >>>>>>>>>
//        Merchant merchant = merchantMapper.queryByPrimaryKeyForUpdate(orders.getSeller());
//        Assert.notNull(merchant, MchBusinessExceptionCode.OBJECT_DOES_NOT_EXIST.getCode(), "商户不存在");
//        Merchant saveMerchant = new Merchant();
//        saveMerchant.setId(merchant.getId());
//        saveMerchant.setFrozenBalance(merchant.getFrozenBalance().add(orders.getTotalMoney()));
//        saveMerchant.setUpdateDate(new Date());
//        merchantMapper.updateByPrimaryKey(saveMerchant);
//
//        //记录回调记录 >>>>>>>>>
////        CallbackNotify callbackNotify = callbackNotifyMapper.queryBySnAndType(request.getNotifyId(), request.getPaymentMethod().getKey());
////        if (callbackNotify != null) {
////            log.info("{} sn {} repeated callbacks", request.getPaymentMethod().getValue(), request.getNotifyId());
////            return;
////        }
////        CallbackNotify callbackNotify = new CallbackNotify();
//////        callbackNotify.setCallbackSn(request.getNotifyId());
////        callbackNotify.setCallbackSn(outTradeNo);
//////        callbackNotify.setCallbackType(request.getPaymentMethod().getKey());
////        assert aggregateQrcode != null;
////        callbackNotify.setCallbackType(aggregateQrcode.getPaymentWay());
////        callbackNotify.setMetadata(aggregateQrcode.getMetadata());
//////        callbackNotify.setMetadata(JsonParser.serializeToJson(request.getMetadata()));
////        callbackNotify.setCreateDate(new Date());
////        callbackNotify.setUpdateDate(new Date());
////        callbackNotifyMapper.insert(callbackNotify);
//
//        // 线下扫码用户没有个人流水
////                //记录用户流水 >>>>>>>>>
////                MemberBill memberBill = new MemberBill();
////                memberBill.setSerialNo(outTradeNo);
////                memberBill.setAmount(orders.getTailMoney());
////                memberBill.setUserId(orders.getBuyer());
////                memberBill.setType(MemberBill.TYPE.OUT.getKey());
////                memberBill.setTradeType(MemberBill.TRADE_TYPE.ONLINE_PURCHASE.getKey());
////                memberBill.setRemark(MemberBill.TRADE_TYPE.ONLINE_PURCHASE.getValue());
////                memberBill.setSource(merchantGoods.getName());
////                memberBill.setSourceId(orders.getId());
////                memberBill.setCreateDate(new Date());
////                memberBill.setUpdateDate(new Date());
////                memberBillMapper.insert(memberBill);
//
//        //记录商户流水 >>>>>>>>>
//        MerchantBill merchantBill = new MerchantBill();
//        merchantBill.setAmount(orders.getTotalMoney());
//        merchantBill.setMerchantId(orders.getSeller());
//        // 商户该条流水来源
//        merchantBill.setSourceId(orders.getId());
//        merchantBill.setCreateDate(new Date());
//        merchantBill.setUpdateDate(new Date());
//        // 类型-- 1:收入
//        merchantBill.setType(MerchantBill.STATUS.IN.getKey());
//        // 交易类型-- 2:线下扫码
//        merchantBill.setTradeType(MerchantBill.TRADE_STATUS.OFF_LINE.getKey());
//        merchantBill.setRemark(MerchantBill.TRADE_STATUS.OFF_LINE.getValue());
//        merchantBillMapper.insert(merchantBill);
//        // -------------------<<<<<<<<<<<<<----------------------
//    }

}

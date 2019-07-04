package com.miner.disco.front.service.impl;

import com.miner.disco.basic.util.JsonParser;
import com.miner.disco.basic.util.UidMaskUtils;
import com.miner.disco.front.dao.*;
import com.miner.disco.front.exception.BusinessException;
import com.miner.disco.front.model.request.SweepPaymentNotifyRequest;
import com.miner.disco.front.service.SweepPaymentService;
import com.miner.disco.pojo.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author Created by lubycoder@163.com 2019-2-21
 */
@Slf4j
@Service
public class SweepPaymentServiceImpl implements SweepPaymentService {

    @Autowired
    private CallbackNotifyMapper callbackNotifyMapper;

    @Autowired
    private MerchantReceivablesQrcodeMapper merchantReceivablesQrcodeMapper;

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
    private ConfigMapper configMapper;

    @Autowired
    private PlatformBillMapper platformBillMapper;

    @Override
    @Transactional(rollbackFor = RuntimeException.class, isolation = Isolation.READ_COMMITTED)
    public void callback(SweepPaymentNotifyRequest request) throws BusinessException {
        CallbackNotify callbackNotify = callbackNotifyMapper.queryBySnAndType(request.getNotifyId(), request.getPayment().getKey());
        if (callbackNotify != null) {
            log.info("{} sn {} repeated callbacks", request.getPayment().getValue(), request.getNotifyId());
            return;
        }

        // 查询聚合收款码
        MerchantAggregateQrcode aggregateQrcode = merchantAggregateQrcodeMapper.queryByPrimaryKey(request.getQrcodeId());

        if (aggregateQrcode == null) {
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

        // 更新收款码 MerchantAggregateQrcode 状态为2:支付成功
        MerchantAggregateQrcode merchantAggregateQrcode = new MerchantAggregateQrcode();
        BeanUtils.copyProperties(aggregateQrcode,merchantAggregateQrcode);
        merchantAggregateQrcode.setStatus(MerchantAggregateQrcode.STATUS.PAY_SUCCESS.getKey());
        merchantAggregateQrcode.setPaymentWay(request.getPayment().getKey());
        merchantAggregateQrcode.setPaymentDate(new Date());
        merchantAggregateQrcode.setUpdateDate(new Date());
        merchantAggregateQrcodeMapper.updateByPrimaryKey(merchantAggregateQrcode);


        Merchant merchant = merchantMapper.queryByPrimaryKeyForUpdate(merchantAggregateQrcode.getMchId());
        BigDecimal merchantAmount = request.getAmount();
        //记录引导员抽成
        if (request.getVipId() != -1) {
            BigDecimal vipAmount = merchantAggregateQrcode.getOriginalPrice().multiply(merchant.getVipRatio());
            merchantAmount = merchantAmount.subtract(vipAmount);

            //记录VIP引导员流水
            MemberBill memberBill = new MemberBill();
            String sno = String.format("%s%s%s",
                    UidMaskUtils.idToCode(merchant.getId()),
                    UidMaskUtils.idToCode(request.getMemberId()),
                    System.currentTimeMillis());
            memberBill.setSerialNo(sno);
            memberBill.setAmount(vipAmount);
            memberBill.setUserId(request.getVipId());
            memberBill.setType(MemberBill.TYPE.IN.getKey());
            memberBill.setTradeType(MemberBill.TRADE_TYPE.GUIDE_ROYALTY.getKey());
            memberBill.setRemark(MemberBill.TRADE_TYPE.GUIDE_ROYALTY.getValue());
            memberBill.setSource(MemberBill.TRADE_TYPE.GUIDE_ROYALTY.getValue());
            memberBill.setSourceId(merchantAggregateQrcode.getId());
            memberBill.setCreateDate(new Date());
            memberBill.setUpdateDate(new Date());
            memberBillMapper.insert(memberBill);

            //更新VIP引导员余额
            Member member = memberMapper.queryByPrimaryKeyForUpdate(request.getVipId());
            Member saveMember = new Member();
            saveMember.setId(request.getVipId());
            saveMember.setBalance(member.getBalance().add(vipAmount));
            memberMapper.updateByPrimaryKey(saveMember);
        }

        //记录平台抽成
        BigDecimal platformAmount = merchantAggregateQrcode.getOriginalPrice().multiply(merchant.getPlatformRatio());
        merchantAmount = merchantAmount.subtract(platformAmount);

        PlatformBill platformBill = new PlatformBill();
        platformBill.setAmount(platformAmount);
        platformBill.setType(PlatformBill.TYPE.IN.getKey());
        platformBill.setTradeType(PlatformBill.TRADE_TYPE.SWEEP_PAYMENT_TAKE.getKey());
        platformBill.setCreateDate(new Date());
        platformBill.setUpdateDate(new Date());
        platformBillMapper.insert(platformBill);

        //记录用户流水
        String sno = String.format("%s%s%s",
                UidMaskUtils.idToCode(merchant.getId()),
                UidMaskUtils.idToCode(request.getMemberId()),
                System.currentTimeMillis());
        MemberBill memberBill = new MemberBill();
        memberBill.setSerialNo(sno);
        memberBill.setAmount(request.getAmount());
        memberBill.setUserId(request.getMemberId());
        memberBill.setType(MemberBill.TYPE.OUT.getKey());
        memberBill.setTradeType(MemberBill.TRADE_TYPE.OFFLINE_PAYMENT.getKey());
        memberBill.setRemark(MemberBill.TRADE_TYPE.OFFLINE_PAYMENT.getValue());
        memberBill.setSource(MemberBill.TRADE_TYPE.OFFLINE_PAYMENT.getValue());
        memberBill.setSourceId(merchantAggregateQrcode.getId());
        memberBill.setCreateDate(new Date());
        memberBill.setUpdateDate(new Date());
        memberBillMapper.insert(memberBill);

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
        Merchant saveMerchant = merchantMapper.queryByPrimaryKeyForUpdate(merchant.getId());
        saveMerchant.setId(merchant.getId());
        saveMerchant.setUsableBalance(merchant.getUsableBalance().add(merchantAmount));
        merchantMapper.updateByPrimaryKey(saveMerchant);

    }

}

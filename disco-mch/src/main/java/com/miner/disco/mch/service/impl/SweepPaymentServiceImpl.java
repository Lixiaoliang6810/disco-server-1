package com.miner.disco.mch.service.impl;

import com.miner.disco.basic.util.JsonParser;
import com.miner.disco.basic.util.ShareCodeUtils;
import com.miner.disco.basic.util.UidMaskUtils;
import com.miner.disco.mch.dao.*;
import com.miner.disco.mch.exception.MchBusinessException;
import com.miner.disco.mch.model.request.SweepPaymentNotifyRequest;
import com.miner.disco.mch.service.SweepPaymentService;
import com.miner.disco.pojo.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

}

package com.miner.disco.mch.service.impl;

import com.alipay.api.AlipayApiException;
import com.alipay.api.response.AlipayTradePrecreateResponse;
import com.google.gson.JsonObject;
import com.miner.disco.alipay.support.AlipayService;
import com.miner.disco.alipay.support.model.request.AlipayPreorderRequest;
import com.miner.disco.basic.assertion.Assert;
import com.miner.disco.basic.constants.BasicConst;
import com.miner.disco.basic.constants.BooleanStatus;
import com.miner.disco.basic.constants.Environment;
import com.miner.disco.basic.util.DtoTransition;
import com.miner.disco.basic.util.ShareCodeUtils;
import com.miner.disco.basic.util.UidMaskUtils;
import com.miner.disco.mch.component.SerialNoGenerator;
import com.miner.disco.mch.consts.Const;
import com.miner.disco.mch.dao.*;
import com.miner.disco.mch.exception.MchBusinessException;
import com.miner.disco.mch.exception.MchBusinessExceptionCode;
import com.miner.disco.mch.model.request.*;
import com.miner.disco.mch.model.response.CheckReceivablesStatusResponse;
import com.miner.disco.mch.model.response.MerchantDetailsResponse;
import com.miner.disco.mch.model.response.ReceivablesQrcodeResponse;
import com.miner.disco.mch.service.MerchantService;
import com.miner.disco.pojo.*;
import com.miner.disco.wxpay.support.exception.WxpayApiException;
import com.zaki.pay.wx.constants.WXOrderStatus;
import com.zaki.pay.wx.model.request.ApplyRefundRequest;
import com.zaki.pay.wx.model.request.WXPayOrderQueryRequest;
import com.zaki.pay.wx.model.request.WXPayUnifiedOrderRequest;
import com.zaki.pay.wx.model.response.ApplyRefundResponse;
import com.zaki.pay.wx.model.response.QrCodeResponse;
import com.zaki.pay.wx.model.response.WXPayOrderQueryResponse;
import com.zaki.pay.wx.model.response.WXPayUnifiedOrderResponse;
import com.zaki.pay.wx.service.WXPayService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.UUID;

/**
 * @author Created by lubycoder@163.com 2019/1/8
 */
@Slf4j
@Service
public class MerchantServiceImpl implements MerchantService {

    @Autowired
    private MerchantMapper merchantMapper;

    @Autowired
    private MerchantGoodsMapper merchantGoodsMapper;

    @Autowired
    private SerialNoGenerator serialNoGenerator;

    @Autowired
    private MemberMapper memberMapper;

    @Autowired
    private OrdersMapper ordersMapper;

    @Autowired
    private MerchantBillMapper merchantBillMapper;

    @Autowired
    private MemberBillMapper memberBillMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Resource(name = "redisTemplate")
    private ValueOperations<String, String> vOps;

    @Autowired
    private ClassifyMapper classifyMapper;

    @Autowired
    private MerchantAggregateQrcodeMapper merchantAggregateQrcodeMapper;

    @Autowired
    private AlipayService alipayService;

    @Autowired
    private WXPayService wxPayService;

    @Value("${server.environment}")
    private Environment environment;

    @Value("${server.payment-callback-url}")
    private String paymentCallbackUrl;

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public Long register(MerchantRegisterRequest request) throws MchBusinessException {
        String cacheCode = vOps.get(String.format(Const.REDIS_CACHE_SMS_PREFIX, request.getMobile()));
        if (StringUtils.isBlank(cacheCode) || !StringUtils.equals(request.getDigit(), cacheCode)) {
            throw new MchBusinessException(MchBusinessExceptionCode.SMS_CODE_ERROR.getCode(), "验证码错误或已过期");
        }
        Merchant merchant = merchantMapper.queryByMobile(request.getMobile());
        if (merchant != null) {
            throw new MchBusinessException(MchBusinessExceptionCode.MOBILE_REGISTERED.getCode(), "该手机号已被注册");
        }
        merchant = (Merchant) DtoTransition.trans(Merchant.class, request);
        Assert.notNull(merchant, MchBusinessExceptionCode.OBJECT_CONVERSION_ERROR.getCode(), "数据转换错误");
        merchant.setPassword(passwordEncoder.encode(request.getPassword()));
        merchant.setStatus(Merchant.STATUS.WAIT_SUBMIT.getKey());
        merchant.setSort(0);
        merchant.setScore(4.0);
        merchant.setRecommend(BooleanStatus.NO.getKey());
        merchant.setCreateDate(new Date());
        merchant.setUpdateDate(new Date());
        merchant.setUsableBalance(BigDecimal.ZERO);
        merchant.setFrozenBalance(BigDecimal.ZERO);
        merchantMapper.insert(merchant);
        return merchant.getId();
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void modify(MerchantInfoModifyRequest request) throws MchBusinessException {
        Merchant merchant = (Merchant) DtoTransition.trans(Merchant.class, request);
        Assert.notNull(merchant, MchBusinessExceptionCode.OBJECT_CONVERSION_ERROR.getCode(), "数据转换错误");
        merchantMapper.updateByPrimaryKey(merchant);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void resetPassword(MerchantRestPasswordRequest request) throws MchBusinessException {
        String cacheCode = vOps.get(String.format(Const.REDIS_CACHE_SMS_PREFIX, request.getMobile()));
        if (StringUtils.isBlank(cacheCode) || !StringUtils.equals(request.getDigit(), cacheCode)) {
            throw new MchBusinessException(MchBusinessExceptionCode.SMS_CODE_ERROR.getCode(), "验证码错误或已过期");
        }
        Merchant merchant = merchantMapper.queryByMobile(request.getMobile());
        Assert.notNull(merchant, MchBusinessExceptionCode.OBJECT_DOES_NOT_EXIST.getCode(), "商户不存在");
        Merchant saveMerchant = new Merchant();
        saveMerchant.setId(merchant.getId());
        saveMerchant.setPassword(passwordEncoder.encode(request.getPassword()));
        merchantMapper.updateByPrimaryKey(saveMerchant);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void apply(MerchantApplyRequest request) throws MchBusinessException {
        Merchant merchant = (Merchant) DtoTransition.trans(Merchant.class, request);
        Assert.notNull(merchant, MchBusinessExceptionCode.OBJECT_CONVERSION_ERROR.getCode(), "数据转换错误");
        merchant.setStatus(Merchant.STATUS.WAIT_APPROVE.getKey());
        merchantMapper.updateByPrimaryKey(merchant);
    }

    @Override
    public boolean exist(String mobile) throws MchBusinessException {
        return merchantMapper.queryByMobile(mobile) != null;
    }



    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public QrCodeResponse unifiedOrder(ReceivablesQrcodeRequest request, HttpServletRequest servletRequest){
        WXPayUnifiedOrderRequest wxPayUnifiedOrderRequest = new WXPayUnifiedOrderRequest();
        Merchant merchant = merchantMapper.queryByPrimaryKey(request.getMerchantId());
        // body
        wxPayUnifiedOrderRequest.setBody(merchant.getName());
        // outTradeNo
        String mchUidMask = UidMaskUtils.idToCode(merchant.getId());
        String outTradeNo = String.format("%s%s", mchUidMask, UUID.randomUUID().toString().replaceAll("-", "").toUpperCase().substring(0, 18));
        wxPayUnifiedOrderRequest.setOutTradeNo(outTradeNo);
        // originalPrice
        BigDecimal originalPrice = request.getWinePrice().add(request.getFoodPrice());
        wxPayUnifiedOrderRequest.setTotalFee(environment == Environment.RELEASE ? originalPrice.multiply(BigDecimal.valueOf(100)).toPlainString() : "1");
        // discountPrice
        BigDecimal discountPrice = calculatePrice(request);
        // notifyUrl
        String callbackUrl = (environment == Environment.RELEASE) ? getPath(servletRequest) : paymentCallbackUrl;
        wxPayUnifiedOrderRequest.setNotifyUrl(String.format("%s%s", callbackUrl, "/wxpay/orders/notify"));

        WXPayUnifiedOrderResponse response;
        try {
            // 支付预备
            response =wxPayService.unifiedOrder(wxPayUnifiedOrderRequest);
            if (!"SUCCESS".equals(response.getReturnCode())) {
                throw new MchBusinessException(MchBusinessExceptionCode.QRCODE_GENERATE_ERROR.getCode(), "生成二维码失败");
            }
        }catch (WxpayApiException e){
            log.error("call wxpay api error e={}", e.getMessage());
            throw new MchBusinessException(MchBusinessExceptionCode.QRCODE_GENERATE_ERROR.getCode(), "生成二维码失败");
        }
        // 生成收款码
        DecimalFormat decimalFormat = new DecimalFormat("#0.00");
        genMchAggregateQrcode(request,merchant,outTradeNo,response.getCodeUrl(),originalPrice,new BigDecimal(decimalFormat.format(discountPrice)),2);

        // 生成线下订单
        genOfflineOrder(request,outTradeNo,"微信线下扫码用户-未付款",discountPrice);

        QrCodeResponse qrCodeResponse = new QrCodeResponse();
        qrCodeResponse.setQrcode(response.getCodeUrl());
        qrCodeResponse.setOutTradeNo(outTradeNo);
        qrCodeResponse.setOriginalPrice(originalPrice.toPlainString());
        qrCodeResponse.setDiscountPrice(discountPrice.toPlainString());
        return qrCodeResponse;
    }

    @Transactional(rollbackFor = RuntimeException.class)
    public void genOfflineOrder(ReceivablesQrcodeRequest request, String outTradeNo, String openid,BigDecimal discountPrice) {
        Orders orders = new Orders();

        orders.setNo(outTradeNo);
        // 线下微信扫码用户---该用户可能不是app会员
        // 支付成功后传该用户的微信id--openid,未支付时传-1
        orders.setBuyer(-1L);
        orders.setFullname(openid);
        orders.setSeller(request.getMerchantId());
        orders.setStatus(Orders.STATUS.WAIT_PAYMENT.getKey());
        orders.setAssembleSeats(BooleanStatus.NO.getKey());
        orders.setAssembleSeatsCount(0);
        orders.setAssembleSeatsSurplus(0);
        orders.setAssembleSeatsMoney(BigDecimal.ZERO);
        // 线下支付无定金--极端点：若改用户在app预定，又在线下退款，则在此处定金设0，预定定金可极速退款
        orders.setEarnestMoney(BigDecimal.ZERO);
        orders.setTailMoney(discountPrice);
        orders.setTotalMoney(discountPrice);
        orders.setStatus(Orders.STATUS.WAIT_PAYMENT.getKey());
        orders.setCreateDate(new Date());
        orders.setUpdateDate(new Date());
        ordersMapper.insert(orders);
//        return orders.getId();
    }


    /**
     * 计算客户实际应付金额
     * @return 应付金额
     */
    private BigDecimal calculatePrice(ReceivablesQrcodeRequest receivablesQrcodeRequest){
        Merchant merchant = merchantMapper.queryByPrimaryKey(receivablesQrcodeRequest.getMerchantId());
        // discountPrice==打折金额*折扣+不打折金额
        /*
         * 有引导员优惠码时才打折，折扣继承于商家设定的折扣；
         * 优惠码需要校验
         */
        BigDecimal discountPrice;
        String receivedCoupon = receivablesQrcodeRequest.getCoupon();
        boolean isEffectiveCoupon = false;
        if(StringUtils.isNotBlank(receivedCoupon)) {
            // 校验 coupon
            Long memberId = ShareCodeUtils.codeToId(receivedCoupon);
            Member member = memberMapper.queryByPrimaryKey(memberId);
            if (member != null) {
                String coupon = member.getCoupon();
                if (receivedCoupon.compareTo(coupon) == 0) {
                    isEffectiveCoupon = true;
                }
            }
        }
        if(isEffectiveCoupon){
            discountPrice = receivablesQrcodeRequest.getFoodPrice().subtract(receivablesQrcodeRequest.getFoodPrice().multiply(merchant.getMemberRatio()==null?BigDecimal.ZERO:merchant.getMemberRatio()));
            discountPrice = receivablesQrcodeRequest.getWinePrice().add(discountPrice);
        }else {
            discountPrice = receivablesQrcodeRequest.getWinePrice().add(receivablesQrcodeRequest.getFoodPrice());
        }
        return discountPrice;
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public WXPayOrderQueryResponse queryOrder(String outTradeNo) {
        WXPayOrderQueryResponse response=null;
        WXPayOrderQueryRequest request = new WXPayOrderQueryRequest();
        request.setOutTradeNo(outTradeNo);
        try {
            response = wxPayService.queryOrder(request);
            if(response==null) return null;
            String tradeState = response.getTradeState();
            if("SUCCESS".equals(tradeState)){
                doUpdateBizAsync(response,outTradeNo);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }

    /**
     * 异步执行支付成功后的业务
     *
     * @param response
     * @param outTradeNo
     */
    private void doUpdateBizAsync(WXPayOrderQueryResponse response,String outTradeNo){
        // 更新收款码状态为2
        MerchantAggregateQrcode aggregateQrcode = merchantAggregateQrcodeMapper.queryByOutTradeNo(outTradeNo);
        MerchantAggregateQrcode merchantAggregateQrcode = new MerchantAggregateQrcode();
        if(aggregateQrcode!=null){
            BeanUtils.copyProperties(aggregateQrcode,merchantAggregateQrcode);
            merchantAggregateQrcode.setStatus(WXOrderStatus.PAY_SUCCESS.getKey());
            merchantAggregateQrcode.setPaymentDate(new Date());
            merchantAggregateQrcodeMapper.updateByPrimaryKey(merchantAggregateQrcode);
        }
        // ------------------->>>>>>>>>>>>----------------------

        //更新订单信息--支付成功
        Orders orders = ordersMapper.queryByOutTradeNo(outTradeNo);

        Orders saveOrders = new Orders();
        BeanUtils.copyProperties(orders,saveOrders);
        // 支付成功后将姓名改成微信用户id-- openid
        saveOrders.setBuyer(-1L);
        saveOrders.setFullname(StringUtils.isBlank(response.getOpenId())?"微信线下扫码用户-已付款":response.getOpenId());
        saveOrders.setUpdateDate(new Date());
        saveOrders.setPaymentDate(new Date());
        saveOrders.setStatus(Orders.STATUS.COMPLETE.getKey());
        ordersMapper.updateByPrimaryKey(saveOrders);
        //更新商品信息
        MerchantGoods merchantGoods = merchantGoodsMapper.queryByPrimaryKey(orders.getGoodsId());
        if(merchantGoods!=null){
            MerchantGoods saveMerchantGoods = new MerchantGoods();
            saveMerchantGoods.setId(orders.getGoodsId());
            saveMerchantGoods.setSalesVolume(merchantGoods.getSalesVolume() == null ? 1 : merchantGoods.getSalesVolume() + 1);
            merchantGoodsMapper.updateByPrimaryKey(saveMerchantGoods);
        }

        //更新商户余额
        Merchant merchant = merchantMapper.queryByPrimaryKeyForUpdate(orders.getSeller());
        Assert.notNull(merchant, MchBusinessExceptionCode.OBJECT_DOES_NOT_EXIST.getCode(), "商户不存在");
        Merchant saveMerchant = new Merchant();
        saveMerchant.setId(merchant.getId());
        saveMerchant.setFrozenBalance(merchant.getFrozenBalance().add(orders.getTotalMoney()));
        saveMerchant.setUpdateDate(new Date());
        merchantMapper.updateByPrimaryKey(saveMerchant);

        //记录回调记录

        // 线下扫码用户没有个人流水
//                //记录用户流水
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

        //记录商户流水
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
        // ------------------->>>>>>>>>>>>----------------------
    }
    @Override
    public ApplyRefundResponse refund(ApplyRefundRequest request) {
        return wxPayService.refund(request);
    }

    /**
     * 生成收款二维码
     */
    @Transactional(rollbackFor = RuntimeException.class)
    public void genMchAggregateQrcode(ReceivablesQrcodeRequest receivablesQrcodeRequest,Merchant merchant,String outTradeNo,String qrcode,BigDecimal totalPrice,BigDecimal discountPrice,Integer payway){
        JsonObject ratioMetadata = new JsonObject();
        ratioMetadata.addProperty("vipRatio", merchant.getVipRatio());
        ratioMetadata.addProperty("memberRatio", merchant.getMemberRatio());
        ratioMetadata.addProperty("platformRatio", merchant.getPlatformRatio());
        MerchantAggregateQrcode merchantAggregateQrcode = new MerchantAggregateQrcode();
        merchantAggregateQrcode.setOutTradeNo(outTradeNo);
        merchantAggregateQrcode.setTotalPrice(totalPrice);
        merchantAggregateQrcode.setWinePrice(receivablesQrcodeRequest.getWinePrice());
        merchantAggregateQrcode.setFoodPrice(receivablesQrcodeRequest.getFoodPrice());
        merchantAggregateQrcode.setDiscountPrice(discountPrice);
        merchantAggregateQrcode.setMchId(merchant.getId());
        merchantAggregateQrcode.setMetadata(ratioMetadata.toString());
        merchantAggregateQrcode.setCoupon(receivablesQrcodeRequest.getCoupon());
        // 生成收款二维码
        merchantAggregateQrcode.setQrcode(qrcode);
        if(1==payway){
            merchantAggregateQrcode.setPaymentWay(MerchantAggregateQrcode.PAYMENT_WAY.ALIPAY.getKey());
        }else if(2==payway){
            merchantAggregateQrcode.setPaymentWay(MerchantAggregateQrcode.PAYMENT_WAY.WXPAY.getKey());
        }
        merchantAggregateQrcode.setStatus(WXOrderStatus.WAIT_PAYMENT.getKey());
        merchantAggregateQrcode.setCreateDate(new Date());
        merchantAggregateQrcode.setUpdateDate(new Date());
        merchantAggregateQrcodeMapper.insert(merchantAggregateQrcode);
    }

    private ReceivablesQrcodeResponse alipayment(ReceivablesQrcodeRequest receivablesQrcodeRequest, HttpServletRequest servletRequest){
        Merchant merchant = merchantMapper.queryByPrimaryKey(receivablesQrcodeRequest.getMerchantId());
        String mchUidMask = UidMaskUtils.idToCode(merchant.getId());
        String outTradeNo = String.format("%s%s", mchUidMask, UUID.randomUUID().toString().replaceAll("-", "").toUpperCase().substring(0, 18));
        BigDecimal discountPrice = receivablesQrcodeRequest.getFoodPrice().subtract(receivablesQrcodeRequest.getFoodPrice().multiply(merchant.getMemberRatio()));
        discountPrice = receivablesQrcodeRequest.getWinePrice().add(discountPrice);

        String callbackUrl = (environment == Environment.RELEASE) ? getPath(servletRequest) : paymentCallbackUrl;
        AlipayPreorderRequest alipayPreorderRequest = new AlipayPreorderRequest();
        alipayPreorderRequest.setBody("麦罗佛伦");
        alipayPreorderRequest.setSubject("线下扫码");
        DecimalFormat decimalFormat = new DecimalFormat("#0.00");
        alipayPreorderRequest.setTotalAmount(decimalFormat.format(discountPrice));
        alipayPreorderRequest.setOutTradeNo(outTradeNo);
        alipayPreorderRequest.setProductCode("OFFLINE_PAYMENT");
        alipayPreorderRequest.setCallbackUrl(String.format("%s%s", callbackUrl, "/aggregate/alipay/sweep/notify"));
        AlipayTradePrecreateResponse alipayTradePrecreateResponse;
        try {
            alipayTradePrecreateResponse = alipayService.qrcodePreorder(alipayPreorderRequest);
            log.info(alipayTradePrecreateResponse.getBody());
            if (!"10000".equals(alipayTradePrecreateResponse.getCode())) {
                throw new MchBusinessException(MchBusinessExceptionCode.QRCODE_GENERATE_ERROR.getCode(), "生成二维码失败");
            }
        } catch (AlipayApiException e) {
            log.error("call wxpay api error e={}", e.getErrMsg());
            throw new MchBusinessException(MchBusinessExceptionCode.QRCODE_GENERATE_ERROR.getCode(), "生成二维码失败");
        }
        BigDecimal totalPrice = receivablesQrcodeRequest.getFoodPrice().add(receivablesQrcodeRequest.getWinePrice());
        // 生成收款码
        genMchAggregateQrcode(receivablesQrcodeRequest,merchant,outTradeNo,alipayTradePrecreateResponse.getQrCode(),totalPrice,new BigDecimal(decimalFormat.format(discountPrice)),1);

        ReceivablesQrcodeResponse response = new ReceivablesQrcodeResponse();
        response.setQrcode(alipayTradePrecreateResponse.getQrCode());
        response.setOutTradeNo(outTradeNo);
        response.setOriginalPrice(totalPrice);
        response.setDiscountPrice(new BigDecimal(decimalFormat.format(discountPrice)));
        return response;

    }

    @Override
    @Transactional(readOnly = true)
    public MerchantDetailsResponse details(Long merchantId) throws MchBusinessException {
        Merchant merchant = merchantMapper.queryByPrimaryKey(merchantId);
        MerchantDetailsResponse response = (MerchantDetailsResponse) DtoTransition.trans(MerchantDetailsResponse.class, merchant);
        Assert.notNull(response, MchBusinessExceptionCode.OBJECT_CONVERSION_ERROR.getCode(), "数据转换错误");
        Classify classify = classifyMapper.queryByPrimaryKey(merchant.getClassifyId());
        response.setClassify(classify != null ? classify.getName() : BasicConst.EMPTY);
        return response;
    }

    @Override
    @Transactional(readOnly = false)
    public CheckReceivablesStatusResponse receivablesStatus(Long merchantId, String outTradeNo) throws MchBusinessException {
        MerchantAggregateQrcode aggregateQrcode = merchantAggregateQrcodeMapper.queryByOutTradeNo(outTradeNo);
//        WXPayOrderQueryResponse response = null;
        if(StringUtils.isNotBlank(outTradeNo)) {
            this.queryOrder(outTradeNo);
        }
        CheckReceivablesStatusResponse receivablesStatusResponse = new CheckReceivablesStatusResponse();
        receivablesStatusResponse.setStatus(aggregateQrcode != null ? aggregateQrcode.getStatus() : -1);
        receivablesStatusResponse.setAmount(aggregateQrcode == null ? BigDecimal.ZERO : aggregateQrcode.getDiscountPrice());
        return receivablesStatusResponse;
    }

    private String getPath(HttpServletRequest request) {
        return String.format("%s://%s:%s", request.getScheme(), request.getServerName(), request.getServerPort());
    }



}

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
import com.miner.disco.basic.util.*;
import com.miner.disco.mch.consts.Const;
import com.miner.disco.mch.dao.ClassifyMapper;
import com.miner.disco.mch.dao.MerchantAggregateQrcodeMapper;
import com.miner.disco.mch.dao.MerchantMapper;
import com.miner.disco.mch.dao.MerchantReceivablesQrcodeMapper;
import com.miner.disco.mch.exception.MchBusinessException;
import com.miner.disco.mch.exception.MchBusinessExceptionCode;
import com.miner.disco.mch.model.request.*;
import com.miner.disco.mch.model.response.CheckReceivablesStatusResponse;
import com.miner.disco.mch.model.response.MerchantDetailsResponse;
import com.miner.disco.mch.model.response.ReceivablesQrcodeResponse;
import com.miner.disco.mch.oauth.model.CustomUserDetails;
import com.miner.disco.mch.service.MerchantService;
import com.miner.disco.pojo.Classify;
import com.miner.disco.pojo.Merchant;
import com.miner.disco.pojo.MerchantAggregateQrcode;
import com.miner.disco.pojo.MerchantReceivablesQrcode;
import com.miner.disco.wxpay.support.WxpayService;
import com.miner.disco.wxpay.support.exception.WxpayApiException;
import com.miner.disco.wxpay.support.model.request.WxpayPreorderRequest;
import com.miner.disco.wxpay.support.model.response.WxpayPreorderResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
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
    private PasswordEncoder passwordEncoder;

    @Resource(name = "redisTemplate")
    private ValueOperations<String, String> vOps;

    @Autowired
    private ClassifyMapper classifyMapper;

    @Autowired
    private MerchantReceivablesQrcodeMapper merchantReceivablesQrcodeMapper;

    @Autowired
    private MerchantAggregateQrcodeMapper merchantAggregateQrcodeMapper;

    @Autowired
    private AlipayService alipayService;

    @Autowired
    private WxpayService wxpayService;

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
    public ReceivablesQrcodeResponse receivablesQrcode(ReceivablesQrcodeRequest receivablesQrcodeRequest, HttpServletRequest servletRequest) throws MchBusinessException, UnsupportedEncodingException {
        String useragent = servletRequest.getHeader("user-agent");
        if(useragent!=null && useragent.contains("AlipayClient")){
            return alipayment(receivablesQrcodeRequest,servletRequest);
        }else if (useragent!=null && useragent.contains("MicroMessenger")){
            return wxpayment(receivablesQrcodeRequest,servletRequest);
        }else if (useragent!=null && useragent.contains("Android")){
            return wxpayment(receivablesQrcodeRequest,servletRequest);
        }else {
            return wxpayment(receivablesQrcodeRequest,servletRequest);
        }
    }

    private WxpayPreorderRequest getWxpayPreorderRequest(String sn, BigDecimal amount, String callbackUrl) {
        WxpayPreorderRequest wxpayPreorderRequest = new WxpayPreorderRequest();
        wxpayPreorderRequest.setBody("麦罗佛伦");
        wxpayPreorderRequest.setDetail("线上预定");
        wxpayPreorderRequest.setOutTradeNo(sn);
//        wxpayPreorderRequest.setCallbackParam(callbackParam);
        // 线上记得打开
        wxpayPreorderRequest.setTotalFee(environment == Environment.RELEASE ? amount.multiply(BigDecimal.valueOf(100)).toPlainString() : "1");
//        String callbackUrl = (environment == Environment.RELEASE) ? getPath(servletRequest) : paymentCallbackUrl;
        wxpayPreorderRequest.setCallbackUrl(String.format("%s%s", callbackUrl, "/wxpay/orders/notify"));
        return wxpayPreorderRequest;
    }

    private ReceivablesQrcodeResponse wxpayment(ReceivablesQrcodeRequest receivablesQrcodeRequest, HttpServletRequest servletRequest) {
        Merchant merchant = merchantMapper.queryByPrimaryKey(receivablesQrcodeRequest.getMerchantId());
        String mchUidMask = UidMaskUtils.idToCode(merchant.getId());
        String outTradeNo = String.format("%s%s", mchUidMask, UUID.randomUUID().toString().replaceAll("-", "").toUpperCase().substring(0, 18));
        // discountPrice==打折金额*折扣+不打折金额
        BigDecimal discountPrice = receivablesQrcodeRequest.getFoodPrice().subtract(receivablesQrcodeRequest.getFoodPrice().multiply(merchant.getMemberRatio()));
        discountPrice = receivablesQrcodeRequest.getWinePrice().add(discountPrice);
        // 微信预支付请求
        String callbackUrl = (environment == Environment.RELEASE) ? getPath(servletRequest) : paymentCallbackUrl;
        // 微信支付需将金额discountPrice放大100倍
        WxpayPreorderRequest wxpayPreorderRequest = getWxpayPreorderRequest(outTradeNo, discountPrice, callbackUrl);
        WxpayPreorderResponse wxpayPreorderResponse;
        try {
            wxpayPreorderResponse = wxpayService.preorder(wxpayPreorderRequest);
            log.info(wxpayPreorderResponse.getPackageStr());
            if (!"SUCCESS".equals(wxpayPreorderResponse.getReturnCode())) {
                throw new MchBusinessException(MchBusinessExceptionCode.QRCODE_GENERATE_ERROR.getCode(), "生成二维码失败");
            }
        }catch (WxpayApiException e){
            log.error("call wxpay api error e={}", e.getMessage());
            throw new MchBusinessException(MchBusinessExceptionCode.QRCODE_GENERATE_ERROR.getCode(), "生成二维码失败");
        }
        // 生成收款码
        BigDecimal totalPrice = receivablesQrcodeRequest.getFoodPrice().add(receivablesQrcodeRequest.getWinePrice());
        DecimalFormat decimalFormat = new DecimalFormat("#0.00");

        genMchAggregateQrcode(receivablesQrcodeRequest,merchant,outTradeNo,totalPrice,wxpayPreorderResponse.getCodeUrl(),new BigDecimal(decimalFormat.format(discountPrice)));

        ReceivablesQrcodeResponse response = new ReceivablesQrcodeResponse();
        response.setQrcode(wxpayPreorderResponse.getCodeUrl());
        response.setOutTradeNo(outTradeNo);
        response.setOriginalPrice(totalPrice);
        BigDecimal responseDiscountPrice = discountPrice;
        response.setDiscountPrice(new BigDecimal(decimalFormat.format(responseDiscountPrice)));
//        System.out.println("收款码："+response.getQrcode());
        return response;
    }

    private void genMchAggregateQrcode(ReceivablesQrcodeRequest receivablesQrcodeRequest,Merchant merchant,String outTradeNo,BigDecimal totalPrice,String qrcode,BigDecimal discountPrice){
        JsonObject ratioMetadata = new JsonObject();
        ratioMetadata.addProperty("vipRatio", merchant.getVipRatio());
        ratioMetadata.addProperty("memberRatio", merchant.getMemberRatio());
        ratioMetadata.addProperty("platformRatio", merchant.getPlatformRatio());
        MerchantAggregateQrcode merchantAggregateQrcode = new MerchantAggregateQrcode();
        merchantAggregateQrcode.setOutTradeNo(outTradeNo);
        merchantAggregateQrcode.setQrcode(qrcode);
        merchantAggregateQrcode.setTotalPrice(totalPrice);
        merchantAggregateQrcode.setWinePrice(receivablesQrcodeRequest.getWinePrice());
        merchantAggregateQrcode.setFoodPrice(receivablesQrcodeRequest.getFoodPrice());
        merchantAggregateQrcode.setDiscountPrice(discountPrice);
        merchantAggregateQrcode.setMchId(merchant.getId());
        merchantAggregateQrcode.setMetadata(ratioMetadata.toString());
        merchantAggregateQrcode.setCoupon(receivablesQrcodeRequest.getCoupon());
        merchantAggregateQrcode.setStatus(MerchantAggregateQrcode.STATUS.WAIT_PAYMENT.getKey());
        merchantAggregateQrcode.setPaymentWay(MerchantAggregateQrcode.PAYMENT_WAY.ALIPAY.getKey());
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
        genMchAggregateQrcode(receivablesQrcodeRequest,merchant,outTradeNo,totalPrice,alipayTradePrecreateResponse.getQrCode(),new BigDecimal(decimalFormat.format(discountPrice)));

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
    @Transactional(readOnly = true)
    public CheckReceivablesStatusResponse receivablesStatus(Long merchantId, String outTradeNo) throws MchBusinessException {
        MerchantAggregateQrcode aggregateQrcode = merchantAggregateQrcodeMapper.queryByOutTradeNo(outTradeNo);
        CheckReceivablesStatusResponse receivablesStatusResponse = new CheckReceivablesStatusResponse();
        receivablesStatusResponse.setStatus(aggregateQrcode != null ? aggregateQrcode.getStatus() : -1);
        receivablesStatusResponse.setAmount(aggregateQrcode == null ? BigDecimal.ZERO : aggregateQrcode.getDiscountPrice());
        return receivablesStatusResponse;
    }

    private String getPath(HttpServletRequest request) {
        return String.format("%s://%s:%s", request.getScheme(), request.getServerName(), request.getServerPort());
    }
}

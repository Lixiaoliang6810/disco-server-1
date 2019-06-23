package com.miner.disco.mch.service.impl;

import com.miner.disco.basic.assertion.Assert;
import com.miner.disco.basic.constants.BasicConst;
import com.miner.disco.basic.constants.BooleanStatus;
import com.miner.disco.basic.util.DtoTransition;
import com.miner.disco.basic.util.Encrypt;
import com.miner.disco.basic.util.ShareCodeUtils;
import com.miner.disco.mch.consts.Const;
import com.miner.disco.mch.dao.ClassifyMapper;
import com.miner.disco.mch.dao.MerchantMapper;
import com.miner.disco.mch.dao.MerchantReceivablesQrcodeMapper;
import com.miner.disco.mch.exception.MchBusinessException;
import com.miner.disco.mch.exception.MchBusinessExceptionCode;
import com.miner.disco.mch.model.request.MerchantApplyRequest;
import com.miner.disco.mch.model.request.MerchantInfoModifyRequest;
import com.miner.disco.mch.model.request.MerchantRegisterRequest;
import com.miner.disco.mch.model.request.MerchantRestPasswordRequest;
import com.miner.disco.mch.model.response.MerchantDetailsResponse;
import com.miner.disco.mch.model.response.ReceivablesQrcodeResponse;
import com.miner.disco.mch.oauth.model.CustomUserDetails;
import com.miner.disco.mch.service.MerchantService;
import com.miner.disco.pojo.Classify;
import com.miner.disco.pojo.Merchant;
import com.miner.disco.pojo.MerchantReceivablesQrcode;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

/**
 * @author Created by lubycoder@163.com 2019/1/8
 */
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

    /**
     * 生成收款码--确认优惠折扣
     * @param merchantId
     * @param amount
     * @param coupon
     * @return
     * @throws MchBusinessException
     */
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public ReceivablesQrcodeResponse receivablesQrcode(Long merchantId, BigDecimal amount, String coupon) throws MchBusinessException {
        Merchant merchant = merchantMapper.queryByPrimaryKey(merchantId);
        String salt = Encrypt.MD5.encrypt(String.valueOf(merchantId));
        String key = UUID.randomUUID().toString().replaceAll("-", "");
        BigDecimal discountPrice = amount;
        // 扫描引导员优惠码时才折扣
        if(StringUtils.isNotBlank(coupon)){
            if(merchant.getMemberRatio()!=null){
                discountPrice = amount.subtract(amount.multiply(merchant.getMemberRatio()));
            }
        }
        String plaintext = String.format("disco://merchant/receivables?mchId=%s&amount=%s&coupon=%s&key=%s&salt=%s",
                merchantId, discountPrice, coupon != null ? coupon : BasicConst.EMPTY, key, salt);
        String sign = Encrypt.HMACSHA.hmacSha1(plaintext, salt);
        String qrcode = String.format("disco://merchant/receivables?mchId=%s&amount=%s&name=%s&coupon=%s&key=%s&sign=%s",
                merchantId, discountPrice, merchant.getName(), coupon != null ? coupon : BasicConst.EMPTY, key, sign);
        MerchantReceivablesQrcode receivablesQrcode = new MerchantReceivablesQrcode();
        receivablesQrcode.setMchId(merchantId);
        receivablesQrcode.setAmount(amount);
        receivablesQrcode.setKey(key);
        receivablesQrcode.setSalt(salt);
        receivablesQrcode.setQrcode(qrcode);
        receivablesQrcode.setCreateDate(new Date());
        receivablesQrcode.setUpdateDate(new Date());
        receivablesQrcode.setOriginalPrice(amount);
        receivablesQrcode.setDiscountPrice(discountPrice);
        receivablesQrcode.setStatus(MerchantReceivablesQrcode.STATUS.WAIT_PAYMENT.getKey());
        merchantReceivablesQrcodeMapper.insert(receivablesQrcode);
        ReceivablesQrcodeResponse response = new ReceivablesQrcodeResponse();
        response.setQrcode(qrcode);
        response.setOriginalPrice(amount);
        response.setDiscountPrice(discountPrice);
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
    public Integer receivablesStatus(Long merchantId, String key) throws MchBusinessException {
        MerchantReceivablesQrcode receivablesQrcode = merchantReceivablesQrcodeMapper.queryByKey(key);
        return receivablesQrcode != null ? receivablesQrcode.getStatus() : -1;
    }
}

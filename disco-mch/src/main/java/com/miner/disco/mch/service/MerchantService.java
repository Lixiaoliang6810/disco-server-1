package com.miner.disco.mch.service;

import com.miner.disco.mch.exception.MchBusinessException;
import com.miner.disco.mch.model.request.MerchantApplyRequest;
import com.miner.disco.mch.model.request.MerchantInfoModifyRequest;
import com.miner.disco.mch.model.request.MerchantRegisterRequest;
import com.miner.disco.mch.model.request.MerchantRestPasswordRequest;
import com.miner.disco.mch.model.response.MerchantDetailsResponse;
import com.miner.disco.mch.model.response.ReceivablesQrcodeResponse;

import java.math.BigDecimal;

/**
 * @author Created by lubycoder@163.com 2019/1/8
 */
public interface MerchantService {

    Long register(MerchantRegisterRequest request) throws MchBusinessException;

    void modify(MerchantInfoModifyRequest request) throws MchBusinessException;

    void resetPassword(MerchantRestPasswordRequest request) throws MchBusinessException;

    void apply(MerchantApplyRequest request) throws MchBusinessException;

    boolean exist(String mobile) throws MchBusinessException;

    ReceivablesQrcodeResponse receivablesQrcode(Long merchantId, BigDecimal amount, String coupon) throws MchBusinessException;

    MerchantDetailsResponse details(Long merchantId) throws MchBusinessException;

    Integer receivablesStatus(Long merchantId, String key) throws MchBusinessException;

}

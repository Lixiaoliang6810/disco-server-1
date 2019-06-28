package com.miner.disco.mch.service;

import com.miner.disco.mch.exception.MchBusinessException;
import com.miner.disco.mch.model.request.*;
import com.miner.disco.mch.model.response.CheckReceivablesStatusResponse;
import com.miner.disco.mch.model.response.MerchantDetailsResponse;
import com.miner.disco.mch.model.response.ReceivablesQrcodeResponse;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
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

    ReceivablesQrcodeResponse receivablesQrcode(ReceivablesQrcodeRequest receivablesQrcodeRequest, HttpServletRequest servletRequest) throws MchBusinessException, UnsupportedEncodingException;

    MerchantDetailsResponse details(Long merchantId) throws MchBusinessException;

    CheckReceivablesStatusResponse receivablesStatus(Long merchantId, String outTradeNo) throws MchBusinessException;
}

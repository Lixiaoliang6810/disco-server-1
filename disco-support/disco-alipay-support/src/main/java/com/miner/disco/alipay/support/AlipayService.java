package com.miner.disco.alipay.support;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.domain.*;
import com.alipay.api.request.*;
import com.alipay.api.response.*;
import com.miner.disco.alipay.support.model.request.AlipayPreorderRequest;
import com.miner.disco.alipay.support.model.request.AlipayRefundRequest;
import com.miner.disco.alipay.support.model.request.AlipayTransferRequest;
import com.miner.disco.alipay.support.model.response.AlipayPreorderResponse;

/**
 * @author Created by lubycoder@163.com 2019/1/7
 */
public class AlipayService {

    private AlipayClient alipayClient;

    public AlipayService setAlipayClient(AlipayClient alipayClient) {
        this.alipayClient = alipayClient;
        return this;
    }

    public AlipayPreorderResponse appPreorder(AlipayPreorderRequest request) throws AlipayApiException {
        AlipayTradeAppPayRequest alipayTradeAppPayRequest = new AlipayTradeAppPayRequest();
        AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
        model.setBody(request.getBody());
        model.setSubject(request.getSubject());
        model.setOutTradeNo(request.getOutTradeNo());
        model.setTimeoutExpress(request.getTimeoutExpress());
        model.setTotalAmount(request.getTotalAmount());
        model.setProductCode(request.getProductCode());
        model.setPassbackParams(request.getCallbackParam());
        alipayTradeAppPayRequest.setBizModel(model);
        alipayTradeAppPayRequest.setNotifyUrl(request.getCallbackUrl());
        AlipayTradeAppPayResponse alipayTradeAppPayResponse;
        alipayTradeAppPayResponse = alipayClient.sdkExecute(alipayTradeAppPayRequest);
        String payInfo = alipayTradeAppPayResponse.getBody();
        AlipayPreorderResponse response = new AlipayPreorderResponse();
        response.setPayInfo(payInfo);
        return response;
    }

    public AlipayTradeRefundResponse refund(AlipayRefundRequest request) throws AlipayApiException {
        AlipayTradeRefundRequest refundApplyRequest = new AlipayTradeRefundRequest();
        AlipayTradeRefundModel refundModel = new AlipayTradeRefundModel();
        refundModel.setOutTradeNo(request.getOutTradeNo());
        refundModel.setTradeNo(request.getTradeNo());
        refundModel.setRefundAmount(request.getRefundAmount());
        refundModel.setRefundReason(request.getRefundReason());
        refundApplyRequest.setBizModel(refundModel);
        return alipayClient.execute(refundApplyRequest);
    }

    public AlipayPreorderResponse wapPreorder(AlipayPreorderRequest request) throws AlipayApiException {
        AlipayTradeWapPayRequest alipayTradeWapPayRequest = new AlipayTradeWapPayRequest();
        AlipayTradeWapPayModel model = new AlipayTradeWapPayModel();
        model.setBody(request.getBody());
        model.setSubject(request.getSubject());
        model.setOutTradeNo(request.getOutTradeNo());
        model.setTimeoutExpress(request.getTimeoutExpress());
        model.setTotalAmount(request.getTotalAmount());
        model.setProductCode(request.getProductCode());
        model.setPassbackParams(request.getCallbackParam());
        alipayTradeWapPayRequest.setBizModel(model);
        alipayTradeWapPayRequest.setNotifyUrl(request.getCallbackUrl());
        AlipayTradeWapPayResponse alipayTradeWapPayResponse = alipayClient.pageExecute(alipayTradeWapPayRequest);
        AlipayPreorderResponse response = new AlipayPreorderResponse();
        response.setPayInfo(alipayTradeWapPayResponse.getBody());
        return response;
    }

    public AlipayFundTransToaccountTransferResponse transfer(AlipayTransferRequest request) throws AlipayApiException {
        AlipayFundTransToaccountTransferRequest alipayFundTransToaccountTransferRequest = new AlipayFundTransToaccountTransferRequest();
        AlipayFundTransToaccountTransferModel model = new AlipayFundTransToaccountTransferModel();
        model.setOutBizNo(request.getOutBizNo());
        model.setAmount(request.getAmount().toPlainString());
        model.setPayeeAccount(request.getPayeeAccount());
        model.setPayeeRealName(request.getPayeeRealname());
        model.setPayeeType(request.getPayeeType().name());
        alipayFundTransToaccountTransferRequest.setBizModel(model);
        return alipayClient.execute(alipayFundTransToaccountTransferRequest);
    }

    public AlipayTradePrecreateResponse qrcodePreorder(AlipayPreorderRequest request) throws AlipayApiException {
        AlipayTradePrecreateRequest alipayTradePrecreateRequest = new AlipayTradePrecreateRequest();
        AlipayTradePrecreateModel model = new AlipayTradePrecreateModel();
        model.setOutTradeNo(request.getOutTradeNo());
        model.setTotalAmount(request.getTotalAmount());
        model.setTimeoutExpress(request.getTimeoutExpress());
        model.setBody(request.getBody());
        model.setSubject(request.getSubject());
        alipayTradePrecreateRequest.setBizModel(model);
        alipayTradePrecreateRequest.setNotifyUrl(request.getCallbackUrl());
        return alipayClient.execute(alipayTradePrecreateRequest);
    }

}

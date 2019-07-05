package com.miner.disco.mch.service;

import com.miner.disco.mch.exception.MchBusinessException;
import com.miner.disco.mch.model.request.SweepPaymentNotifyRequest;

/**
 * @author Created by lubycoder@163.com 2019-06-21
 */
public interface SweepPaymentService {

    void callback(SweepPaymentNotifyRequest request) throws MchBusinessException;

    void doUpdateBizAsync(String outTradeNo);

}

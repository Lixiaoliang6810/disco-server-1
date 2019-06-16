package com.miner.disco.front.service;

import com.miner.disco.front.exception.BusinessException;
import com.miner.disco.front.model.request.SweepPaymentNotifyRequest;

/**
 * @author Created by lubycoder@163.com 2019-2-21
 */
public interface SweepPaymentService {

    void callback(SweepPaymentNotifyRequest request) throws BusinessException;

}

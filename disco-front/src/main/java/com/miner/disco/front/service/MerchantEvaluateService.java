package com.miner.disco.front.service;

import com.miner.disco.front.exception.BusinessException;
import com.miner.disco.front.model.request.OrdersEvaluateRequest;
import com.miner.disco.front.model.request.MerchantEvaluateListRequest;
import com.miner.disco.front.model.response.MerchantEvaluateListResponse;

import java.util.List;

/**
 * @author Created by lubycoder@163.com 2018/12/29
 */
public interface MerchantEvaluateService {

    void evaluate(OrdersEvaluateRequest request) throws BusinessException;


    List<MerchantEvaluateListResponse> list(MerchantEvaluateListRequest request) throws BusinessException;

}

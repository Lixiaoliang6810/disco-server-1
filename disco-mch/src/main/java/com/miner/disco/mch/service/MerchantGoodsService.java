package com.miner.disco.mch.service;

import com.miner.disco.mch.exception.MchBusinessException;
import com.miner.disco.mch.model.request.MerchantGoodsBatchOperationRequest;
import com.miner.disco.mch.model.request.MerchantGoodsCreateRequest;
import com.miner.disco.mch.model.request.MerchantGoodsListRequest;
import com.miner.disco.mch.model.response.MerchantGoodsListResponse;

import java.util.List;

/**
 * @author Created by lubycoder@163.com 2019/1/8
 */
public interface MerchantGoodsService {

    List<MerchantGoodsListResponse> list(MerchantGoodsListRequest request) throws MchBusinessException;

    void create(MerchantGoodsCreateRequest request) throws MchBusinessException;

    void upper(MerchantGoodsBatchOperationRequest request) throws MchBusinessException;

    void lower(MerchantGoodsBatchOperationRequest request) throws MchBusinessException;

}

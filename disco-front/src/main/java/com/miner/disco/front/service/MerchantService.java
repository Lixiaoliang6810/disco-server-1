package com.miner.disco.front.service;

import com.miner.disco.front.exception.BusinessException;
import com.miner.disco.front.model.request.MerchantListRequest;
import com.miner.disco.front.model.response.MerchantDetailsResponse;
import com.miner.disco.front.model.response.MerchantListResponse;

import java.util.List;

/**
 * @author Created by lubycoder@163.com 2018/12/24
 */
public interface MerchantService {

    /**
     * 商家详情
     *
     * @param merchantId 商家主键
     * @return
     * @throws BusinessException
     */
    MerchantDetailsResponse details(Long userId, Long merchantId) throws BusinessException;

    /**
     * 商家列表
     *
     * @return
     * @throws BusinessException
     */
    List<MerchantListResponse> list(MerchantListRequest request) throws BusinessException;

}

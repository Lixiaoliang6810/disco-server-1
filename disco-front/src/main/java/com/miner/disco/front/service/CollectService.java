package com.miner.disco.front.service;

import com.miner.disco.front.exception.BusinessException;
import com.miner.disco.front.model.request.CollectMerchantListRequest;
import com.miner.disco.front.model.response.CollectMerchantListResponse;

import java.util.List;

/**
 * @author Created by lubycoder@163.com 2019/1/7
 */
public interface CollectService {

    /**
     * 收藏商家
     *
     * @param userId     用户主键
     * @param merchantId 商家主键
     * @throws BusinessException
     */
    void collect(Long userId, Long merchantId) throws BusinessException;

    /**
     * 收藏商家列表
     *
     * @param request
     * @return
     * @throws BusinessException
     */
    List<CollectMerchantListResponse> list(CollectMerchantListRequest request) throws BusinessException;

    /**
     * 取消收藏
     *
     * @param userId     用户主键
     * @param merchantId 商家主键
     * @throws BusinessException
     */
    void cancel(Long userId, Long merchantId) throws BusinessException;

}

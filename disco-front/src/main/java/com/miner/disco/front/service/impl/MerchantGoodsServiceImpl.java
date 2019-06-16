package com.miner.disco.front.service.impl;

import com.miner.disco.front.dao.MerchantGoodsMapper;
import com.miner.disco.front.exception.BusinessException;
import com.miner.disco.front.model.response.MerchantGoodsListResponse;
import com.miner.disco.front.service.MerchantGoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Created by lubycoder@163.com 2019/1/7
 */
@Service
public class MerchantGoodsServiceImpl implements MerchantGoodsService {

    @Autowired
    private MerchantGoodsMapper merchantGoodsMapper;

    @Override
    public List<MerchantGoodsListResponse> list(Long merchantId) throws BusinessException {
        return merchantGoodsMapper.queryByMerchantId(merchantId);
    }

}

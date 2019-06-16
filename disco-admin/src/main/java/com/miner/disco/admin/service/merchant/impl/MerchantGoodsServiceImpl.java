package com.miner.disco.admin.service.merchant.impl;

import com.miner.disco.admin.dao.merchant.MerchantGoodsDao;
import com.miner.disco.admin.exception.AdminBuzException;
import com.miner.disco.admin.model.request.merchant.MerchantGoodsSearchRequest;
import com.miner.disco.admin.model.response.merchant.MerchantGoodsDetailResponse;
import com.miner.disco.admin.model.response.merchant.MerchantGoodsListResponse;
import com.miner.disco.admin.service.merchant.MerchantGoodsService;
import com.miner.disco.basic.model.response.PageResponse;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * author:linjw Date:2019/1/4 Time:17:15
 */
@Service
public class MerchantGoodsServiceImpl implements MerchantGoodsService {

    @Autowired
    private MerchantGoodsDao merchantGoodsDao;

    @Override
    public PageResponse queryGoodsByMerchant(MerchantGoodsSearchRequest searchRequest) throws AdminBuzException {
        List<MerchantGoodsListResponse> listResponses = merchantGoodsDao.queryGoodsByMerchant(searchRequest);
        int total = merchantGoodsDao.countMerchant(searchRequest);
        return PageResponse.builder().list(listResponses).total(total).build();
    }

    @Override
    public MerchantGoodsDetailResponse goodsDetail(Long id) throws AdminBuzException {
        return merchantGoodsDao.goodsDetail(id);
    }
}

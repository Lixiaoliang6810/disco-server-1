package com.miner.disco.mch.service.impl;

import com.miner.disco.basic.assertion.Assert;
import com.miner.disco.basic.constants.DeleteStatus;
import com.miner.disco.basic.util.DtoTransition;
import com.miner.disco.mch.dao.MerchantGoodsMapper;
import com.miner.disco.mch.dao.MerchantMapper;
import com.miner.disco.mch.exception.MchBusinessException;
import com.miner.disco.mch.exception.MchBusinessExceptionCode;
import com.miner.disco.mch.model.request.MerchantGoodsBatchOperationRequest;
import com.miner.disco.mch.model.request.MerchantGoodsCreateRequest;
import com.miner.disco.mch.model.request.MerchantGoodsListRequest;
import com.miner.disco.mch.model.response.MerchantGoodsListResponse;
import com.miner.disco.mch.service.MerchantGoodsService;
import com.miner.disco.pojo.MerchantGoods;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @author Created by lubycoder@163.com 2019/1/8
 */
@Service
public class MerchantGoodsServiceImpl implements MerchantGoodsService {

    @Autowired
    private MerchantGoodsMapper merchantGoodsMapper;

    @Override
    public List<MerchantGoodsListResponse> list(MerchantGoodsListRequest request) throws MchBusinessException {
        return merchantGoodsMapper.queryByMerchantId(request);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void create(MerchantGoodsCreateRequest request) throws MchBusinessException {
        MerchantGoods merchantGoods = (MerchantGoods) DtoTransition.trans(MerchantGoods.class, request);
        Assert.notNull(merchantGoods, MchBusinessExceptionCode.OBJECT_CONVERSION_ERROR.getCode(), "数据转换错误");
        merchantGoods.setStatus(MerchantGoods.STATUS.UPPER.getKey());
        merchantGoods.setDeleted(DeleteStatus.NORMAL.getKey());
        merchantGoods.setMinimumCharge(request.getMinimumCharge());
        merchantGoods.setSalesVolume(0);
        merchantGoods.setCreateDate(new Date());
        merchantGoods.setUpdateDate(new Date());
        merchantGoodsMapper.insert(merchantGoods);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void upper(MerchantGoodsBatchOperationRequest request) throws MchBusinessException {

    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void lower(MerchantGoodsBatchOperationRequest request) throws MchBusinessException {
        merchantGoodsMapper.lower(request.getMerchantId(), request.getGoodsIds());
    }
}

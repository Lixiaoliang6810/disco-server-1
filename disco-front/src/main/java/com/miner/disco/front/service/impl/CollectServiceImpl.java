package com.miner.disco.front.service.impl;

import com.miner.disco.basic.constants.DeleteStatus;
import com.miner.disco.front.dao.CollectMapper;
import com.miner.disco.front.exception.BusinessException;
import com.miner.disco.front.model.request.CollectMerchantListRequest;
import com.miner.disco.front.model.response.CollectMerchantListResponse;
import com.miner.disco.front.service.CollectService;
import com.miner.disco.pojo.Collect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @author Created by lubycoder@163.com 2019/1/7
 */
@Service
public class CollectServiceImpl implements CollectService {

    @Autowired
    private CollectMapper collectMapper;

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void collect(Long userId, Long merchantId) throws BusinessException {
        Collect collect = collectMapper.queryByMerchantIdAndUserId(merchantId, userId);
        if (collect != null) return;
        collect = new Collect();
        collect.setUserId(userId);
        collect.setMerchantId(merchantId);
        collect.setCreateDate(new Date());
        collect.setUpdateDate(new Date());
        collect.setDeleted(DeleteStatus.NORMAL.getKey());
        collectMapper.insert(collect);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CollectMerchantListResponse> list(CollectMerchantListRequest request) throws BusinessException {
        return collectMapper.queryByUserId(request);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void cancel(Long userId, Long merchantId) throws BusinessException {
        Collect collect = collectMapper.queryByMerchantIdAndUserId(merchantId, userId);
        if (collect == null) return;
        collect.setDeleted(DeleteStatus.DELETE.getKey());
        collectMapper.updateByPrimaryKey(collect);
    }
}

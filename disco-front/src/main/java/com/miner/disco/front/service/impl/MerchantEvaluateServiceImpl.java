package com.miner.disco.front.service.impl;

import com.miner.disco.basic.assertion.Assert;
import com.miner.disco.basic.util.DtoTransition;
import com.miner.disco.front.dao.MerchantEvaluateMapper;
import com.miner.disco.front.exception.BusinessException;
import com.miner.disco.front.exception.BusinessExceptionCode;
import com.miner.disco.front.model.request.OrdersEvaluateRequest;
import com.miner.disco.front.model.request.MerchantEvaluateListRequest;
import com.miner.disco.front.model.response.MerchantEvaluateListResponse;
import com.miner.disco.pojo.MerchantEvaluate;
import com.miner.disco.front.service.MerchantEvaluateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @author Created by lubycoder@163.com 2018/12/29
 */
@Service
public class MerchantEvaluateServiceImpl implements MerchantEvaluateService {

    @Autowired
    private MerchantEvaluateMapper merchantEvaluateMapper;

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void evaluate(OrdersEvaluateRequest request) throws BusinessException {
        MerchantEvaluate merchantEvaluate = (MerchantEvaluate) DtoTransition.trans(MerchantEvaluate.class, request);
        Assert.notNull(merchantEvaluate, BusinessExceptionCode.OBJECT_CONVERSION_ERROR.getCode(), "数据错误");
        merchantEvaluate.setCreateDate(new Date());
        merchantEvaluate.setUpdateDate(new Date());
        merchantEvaluateMapper.insert(merchantEvaluate);
    }

    @Override
    public List<MerchantEvaluateListResponse> list(MerchantEvaluateListRequest request) throws BusinessException {
        return merchantEvaluateMapper.queryByMerchantId(request);
    }
}

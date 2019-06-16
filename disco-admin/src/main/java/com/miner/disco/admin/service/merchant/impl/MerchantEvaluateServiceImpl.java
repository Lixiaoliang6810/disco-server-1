package com.miner.disco.admin.service.merchant.impl;

import com.miner.disco.admin.dao.merchant.MerchantEvaluateDao;
import com.miner.disco.admin.model.request.merchant.EvaluateSearchRequest;
import com.miner.disco.admin.model.response.merchant.MerchantEvaluateDetailResponse;
import com.miner.disco.admin.model.response.merchant.MerchantEvaluateListResponse;
import com.miner.disco.admin.service.merchant.MerchantEvaluateService;
import com.miner.disco.basic.model.request.Pagination;
import com.miner.disco.basic.model.response.PageResponse;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * author:linjw Date:2019/1/7 Time:18:42
 */
@Service
public class MerchantEvaluateServiceImpl implements MerchantEvaluateService {

    @Autowired
    private MerchantEvaluateDao merchantEvaluateDao;

    @Override
    public PageResponse queryEvaluateList(EvaluateSearchRequest searchRequest) {
        List<MerchantEvaluateListResponse> listResponses = merchantEvaluateDao.queryEvaluateList(searchRequest);
        int total = merchantEvaluateDao.countEvaluateList(searchRequest);
        return new PageResponse(total, listResponses);
    }

    @Override
    public MerchantEvaluateDetailResponse evaluateDetail(Long id) {
        return merchantEvaluateDao.evaluateDetail(id);
    }
}

package com.miner.disco.admin.service.merchant;

import com.miner.disco.admin.model.request.merchant.EvaluateSearchRequest;
import com.miner.disco.admin.model.response.merchant.MerchantEvaluateDetailResponse;
import com.miner.disco.basic.model.request.Pagination;
import com.miner.disco.basic.model.response.PageResponse;

/**
 * author:linjw Date:2019/1/7 Time:18:41
 */
public interface MerchantEvaluateService {

    /**
     * 查询评论列表
     * @param searchRequest
     * @return
     */
    PageResponse queryEvaluateList(EvaluateSearchRequest searchRequest);

    /**
     * 评价详情
     * @param id
     * @return
     */
    MerchantEvaluateDetailResponse evaluateDetail(Long id);
}

package com.miner.disco.admin.dao.merchant;

import com.miner.disco.admin.model.request.merchant.EvaluateSearchRequest;
import com.miner.disco.admin.model.response.merchant.MerchantEvaluateDetailResponse;
import com.miner.disco.admin.model.response.merchant.MerchantEvaluateListResponse;
import com.miner.disco.basic.model.request.Pagination;
import com.miner.disco.mybatis.support.BasicMapper;
import com.miner.disco.pojo.MerchantEvaluate;

import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * author:linjw Date:2019/1/4 Time:18:00
 */
public interface MerchantEvaluateDao extends BasicMapper<MerchantEvaluate> {

    List<MerchantEvaluateListResponse> queryEvaluateList(@Param(value = "search") EvaluateSearchRequest search);

    int countEvaluateList(@Param(value = "search")EvaluateSearchRequest search);

    MerchantEvaluateDetailResponse evaluateDetail(@Param(value = "id") Long id);
}

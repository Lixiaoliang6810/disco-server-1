package com.miner.disco.front.dao;

import com.miner.disco.front.model.request.MerchantEvaluateListRequest;
import com.miner.disco.front.model.response.MerchantEvaluateListResponse;
import com.miner.disco.pojo.MerchantEvaluate;
import com.miner.disco.mybatis.support.BasicMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Created by lubycoder@163.com 2018/12/24
 */
public interface MerchantEvaluateMapper extends BasicMapper<MerchantEvaluate> {

    List<MerchantEvaluateListResponse> queryByMerchantId(MerchantEvaluateListRequest request);

    Integer countByMerchantId(@Param("merchantId") Long merchantId);

    Double calcAverage(@Param("merchantId") Long merchantId);

}

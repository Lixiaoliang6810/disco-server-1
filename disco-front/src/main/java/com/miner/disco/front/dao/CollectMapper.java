package com.miner.disco.front.dao;

import com.miner.disco.front.model.request.CollectMerchantListRequest;
import com.miner.disco.front.model.response.CollectMerchantListResponse;
import com.miner.disco.pojo.Collect;
import com.miner.disco.mybatis.support.BasicMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Created by lubycoder@163.com 2018/12/24
 */
public interface CollectMapper extends BasicMapper<Collect> {

    Integer countByUserId(@Param("userId") Long userId);

    List<CollectMerchantListResponse> queryByUserId(CollectMerchantListRequest request);

    Collect queryByMerchantIdAndUserId(@Param("merchantId") Long merchantId, @Param("userId") Long userId);

}

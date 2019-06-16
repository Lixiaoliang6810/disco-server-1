package com.miner.disco.front.dao;

import com.miner.disco.front.model.request.MerchantListRequest;
import com.miner.disco.front.model.response.MerchantListResponse;
import com.miner.disco.pojo.Merchant;
import com.miner.disco.mybatis.support.BasicMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Created by lubycoder@163.com 2018/12/24
 */
public interface MerchantMapper extends BasicMapper<Merchant> {

    List<MerchantListResponse> queryRecommend();

    List<MerchantListResponse> search(MerchantListRequest request);

    Merchant queryByPrimaryKeyFroUpdate(@Param("id") Long id);

}

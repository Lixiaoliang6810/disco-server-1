package com.miner.disco.front.dao;

import com.miner.disco.front.model.response.MerchantGoodsListResponse;
import com.miner.disco.pojo.MerchantGoods;
import com.miner.disco.mybatis.support.BasicMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Created by lubycoder@163.com 2018/12/24
 */
public interface MerchantGoodsMapper extends BasicMapper<MerchantGoods> {

    List<MerchantGoodsListResponse> queryByMerchantId(@Param("merchantId") Long merchantId);

}

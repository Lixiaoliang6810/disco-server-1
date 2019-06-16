package com.miner.disco.mch.dao;

import com.miner.disco.mch.model.request.MerchantGoodsListRequest;
import com.miner.disco.mch.model.response.MerchantGoodsListResponse;
import com.miner.disco.mybatis.support.BasicMapper;
import com.miner.disco.pojo.MerchantGoods;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author Created by lubycoder@163.com 2019/1/8
 */
public interface MerchantGoodsMapper extends BasicMapper<MerchantGoods> {

    List<MerchantGoodsListResponse> queryByMerchantId(MerchantGoodsListRequest request);

    void lower(@RequestParam("merchantId") Long merchantId, @Param("goodsIds") Long[] ids);

}

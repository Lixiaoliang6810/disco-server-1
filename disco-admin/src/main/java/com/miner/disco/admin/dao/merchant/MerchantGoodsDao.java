package com.miner.disco.admin.dao.merchant;

import com.miner.disco.admin.model.request.merchant.MerchantGoodsSearchRequest;
import com.miner.disco.admin.model.response.merchant.MerchantGoodsDetailResponse;
import com.miner.disco.admin.model.response.merchant.MerchantGoodsListResponse;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * author:linjw Date:2019/1/4 Time:16:52
 */
public interface MerchantGoodsDao {

    List<MerchantGoodsListResponse> queryGoodsByMerchant(@Param(value = "search") MerchantGoodsSearchRequest search);

    int countMerchant(@Param(value = "search")MerchantGoodsSearchRequest search);

    MerchantGoodsDetailResponse goodsDetail(@Param(value = "id") Long id);
}

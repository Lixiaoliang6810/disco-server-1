package com.miner.disco.admin.service.merchant;

import com.miner.disco.admin.exception.AdminBuzException;
import com.miner.disco.admin.model.request.merchant.MerchantGoodsSearchRequest;
import com.miner.disco.admin.model.response.merchant.MerchantGoodsDetailResponse;
import com.miner.disco.basic.model.request.Pagination;
import com.miner.disco.basic.model.response.PageResponse;

/**
 * author:linjw Date:2019/1/4 Time:17:14
 */
public interface MerchantGoodsService {

    /**
     * 商户商品列表
     * @param searchRequest
     * @return
     * @throws AdminBuzException
     */
    PageResponse queryGoodsByMerchant(MerchantGoodsSearchRequest searchRequest) throws AdminBuzException;

    /**
     * 商户详情
     * @param id
     * @return
     * @throws AdminBuzException
     */
    MerchantGoodsDetailResponse goodsDetail(Long id) throws AdminBuzException;

}

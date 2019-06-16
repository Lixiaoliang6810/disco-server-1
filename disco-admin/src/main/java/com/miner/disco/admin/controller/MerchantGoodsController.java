package com.miner.disco.admin.controller;

import com.miner.disco.admin.model.request.merchant.MerchantGoodsSearchRequest;
import com.miner.disco.admin.model.response.merchant.MerchantGoodsDetailResponse;
import com.miner.disco.admin.service.merchant.MerchantGoodsService;
import com.miner.disco.basic.model.response.PageResponse;
import com.miner.disco.basic.model.response.ViewData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 商户商品管理 author:linjw Date:2019/1/4 Time:17:55
 */
@RestController
public class MerchantGoodsController {

    @Autowired
    private MerchantGoodsService merchantGoodsService;

    @GetMapping(value = "/merchant/goods/list")
    public ViewData list(MerchantGoodsSearchRequest searchRequest) {
        PageResponse pageResponse = merchantGoodsService.queryGoodsByMerchant(searchRequest);
        return ViewData.builder().data(pageResponse.getList()).total(pageResponse.getTotal()).message("商户商品列表").build();
    }

    @GetMapping(value = "/merchant/goods/detail")
    public ViewData goodsDetail(Long id){
        MerchantGoodsDetailResponse detailResponse = merchantGoodsService.goodsDetail(id);
        return ViewData.builder().data(detailResponse).message("商品详情").build();
    }

}

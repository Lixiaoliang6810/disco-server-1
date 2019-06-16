package com.miner.disco.front.controller;

import com.miner.disco.basic.model.response.ViewData;
import com.miner.disco.front.consts.Const;
import com.miner.disco.front.model.response.MerchantGoodsListResponse;
import com.miner.disco.front.service.MerchantGoodsService;
import com.miner.disco.pojo.MerchantGoods;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Created by lubycoder@163.com 2018/12/28
 */
@RestController
public class MerchantGoodsController {

    @Autowired
    private MerchantGoodsService merchantGoodsService;

    @GetMapping(value = "/merchant/goods/list", headers = Const.API_VERSION_1_0_0)
    public ViewData list(@RequestParam("merchantId") Long merchantId) {
        List<MerchantGoodsListResponse> response = merchantGoodsService.list(merchantId);
        return ViewData.builder().data(response).message("商品列表").build();
    }

}

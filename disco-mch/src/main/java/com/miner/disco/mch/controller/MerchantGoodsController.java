package com.miner.disco.mch.controller;

import com.miner.disco.basic.model.response.ViewData;
import com.miner.disco.mch.consts.Const;
import com.miner.disco.mch.model.request.MerchantGoodsBatchOperationRequest;
import com.miner.disco.mch.model.request.MerchantGoodsCreateRequest;
import com.miner.disco.mch.model.request.MerchantGoodsListRequest;
import com.miner.disco.mch.model.response.MerchantGoodsListResponse;
import com.miner.disco.mch.oauth.model.CustomUserDetails;
import com.miner.disco.mch.service.MerchantGoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Created by lubycoder@163.com 2019/1/8
 */
@RestController
public class MerchantGoodsController {

    @Autowired
    private MerchantGoodsService merchantGoodsService;

    @GetMapping(value = "/merchant/goods/list", headers = Const.API_VERSION_1_0_0)
    public ViewData list(@AuthenticationPrincipal OAuth2Authentication oAuth2Authentication,
                         MerchantGoodsListRequest request) {
        Long merchantId = ((CustomUserDetails) oAuth2Authentication.getPrincipal()).getId();
        request.setMerchantId(merchantId);
        List<MerchantGoodsListResponse> response = merchantGoodsService.list(request);
        return ViewData.builder().data(response).message("商品列表").build();
    }

    @PostMapping(value = "/merchant/goods/create", headers = Const.API_VERSION_1_0_0)
    public ViewData create(@AuthenticationPrincipal OAuth2Authentication oAuth2Authentication,
                           MerchantGoodsCreateRequest request) {
        Long merchantId = ((CustomUserDetails) oAuth2Authentication.getPrincipal()).getId();
        request.setMerchantId(merchantId);
        merchantGoodsService.create(request);
        return ViewData.builder().message("创建成功").build();
    }

    @PostMapping(value = "/merchant/goods/edit")
    public ViewData edit() {
        return ViewData.builder().build();
    }

    @PostMapping(value = "/merchant/goods/upper", headers = Const.API_VERSION_1_0_0)
    public ViewData upper(@AuthenticationPrincipal OAuth2Authentication oAuth2Authentication,
                          MerchantGoodsBatchOperationRequest request) {
        Long merchantId = ((CustomUserDetails) oAuth2Authentication.getPrincipal()).getId();
        request.setMerchantId(merchantId);
        merchantGoodsService.upper(request);
        return ViewData.builder().message("上架成功").build();
    }

    @PostMapping(value = "/merchant/goods/lower", headers = Const.API_VERSION_1_0_0)
    public ViewData lower(@AuthenticationPrincipal OAuth2Authentication oAuth2Authentication,
                          MerchantGoodsBatchOperationRequest request) {
        Long merchantId = ((CustomUserDetails) oAuth2Authentication.getPrincipal()).getId();
        request.setMerchantId(merchantId);
        merchantGoodsService.lower(request);
        return ViewData.builder().message("下架成功").build();
    }

}

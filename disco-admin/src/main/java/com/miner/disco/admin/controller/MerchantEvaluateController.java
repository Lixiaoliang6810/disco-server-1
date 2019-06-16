package com.miner.disco.admin.controller;

import com.miner.disco.admin.model.request.merchant.EvaluateSearchRequest;
import com.miner.disco.admin.model.response.merchant.MerchantEvaluateDetailResponse;
import com.miner.disco.admin.service.merchant.MerchantEvaluateService;
import com.miner.disco.basic.model.response.PageResponse;
import com.miner.disco.basic.model.response.ViewData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 商户评价管理 author:linjw Date:2019/1/7 Time:18:49
 */
@RestController
public class MerchantEvaluateController {

    @Autowired
    private MerchantEvaluateService merchantEvaluateService;

    @GetMapping(value = "/merchant/evaluate/list")
    public ViewData evaluateList(EvaluateSearchRequest searchRequest) {
        PageResponse pageResponse = merchantEvaluateService.queryEvaluateList(searchRequest);
        return ViewData.builder().data(pageResponse.getList()).total(pageResponse.getTotal()).message("评论列表").build();
    }

    @GetMapping(value = "/merchant/evaluate/detail")
    public ViewData evaluateDetail(Long id) {
        MerchantEvaluateDetailResponse detailResponse = merchantEvaluateService.evaluateDetail(id);
        return ViewData.builder().data(detailResponse).message("评价详情").build();
    }
}

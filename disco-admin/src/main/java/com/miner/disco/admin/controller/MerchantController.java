package com.miner.disco.admin.controller;

import com.miner.disco.admin.auth.annotation.Func;
import com.miner.disco.admin.auth.annotation.Module;
import com.miner.disco.admin.constant.function.Edit;
import com.miner.disco.admin.constant.function.Review;
import com.miner.disco.admin.constant.module.merchant.MerchantShop;
import com.miner.disco.admin.model.request.merchant.*;
import com.miner.disco.admin.model.response.merchant.MerchantDetailResponse;
import com.miner.disco.admin.service.merchant.MerchantService;
import com.miner.disco.basic.model.response.PageResponse;
import com.miner.disco.basic.model.response.ViewData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 商户管理 author:linjw Date:2019/1/4 Time:17:52
 */
@RestController
@Module(MerchantShop.class)
public class MerchantController {

    @Autowired
    private MerchantService merchantService;

    @GetMapping(value = "/merchant/list")
    public ViewData list(MerchantSearchRequest searchRequest) {
        PageResponse pageResponse = merchantService.list(searchRequest);
        return ViewData.builder().data(pageResponse.getList()).total(pageResponse.getTotal()).message("商户列表").build();
    }

    @GetMapping(value = "/merchant/detail")
    public ViewData detail(Long id) {
        MerchantDetailResponse response = merchantService.detail(id);
        return ViewData.builder().data(response).message("商户详情").build();
    }

    @GetMapping(value = "/merchant/auditing/list")
    public ViewData auditingList(AuditingSearchRequest searchRequest) {
        PageResponse pageResponse = merchantService.auditingList(searchRequest);
        return ViewData.builder().data(pageResponse.getList()).total(pageResponse.getTotal()).message("商户审核列表").build();
    }

    @PostMapping(value = "/merchant/auditing")
    @Func(Review.class)
    public ViewData auditing(AuditingRequest auditingRequest) {
        merchantService.auditing(auditingRequest);
        return ViewData.builder().message("审核成功").build();
    }

    @PostMapping(value = "/merchant/modify")
    @Func(Edit.class)
    public ViewData modify(MerchantModifyRequest modifyRequest) {
        merchantService.modify(modifyRequest);
        return ViewData.builder().message("修改成功").build();
    }

    @PutMapping(value = "/merchant/modify/ratio")
    @Func(Edit.class)
    public ViewData modifyRatio(@RequestBody MerchantModifyRatioRequest modifyRequest) {
        merchantService.modifyRatio(modifyRequest);
        return ViewData.builder().message("修改成功").build();
    }
}

package com.miner.disco.admin.controller;

import com.miner.disco.admin.model.request.merchant.MerchantBillSearchRequest;
import com.miner.disco.admin.service.merchant.MerchantBillService;
import com.miner.disco.basic.model.response.PageResponse;
import com.miner.disco.basic.model.response.ViewData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * author:linjw Date:2019/1/14 Time:18:04
 */
@RestController
public class MerchantBillController {

    @Autowired
    private MerchantBillService merchantBillService;

    @GetMapping(value = "/merchant/bill/list")
    public ViewData billList(MerchantBillSearchRequest merchantBillSearchRequest) {
        PageResponse pageResponse = merchantBillService.merchantBillList(merchantBillSearchRequest);
        return ViewData.builder().data(pageResponse.getList()).total(pageResponse.getTotal()).message("商户账单").build();
    }
}

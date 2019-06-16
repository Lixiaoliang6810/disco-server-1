package com.miner.disco.front.controller;

import com.miner.disco.basic.model.response.ViewData;
import com.miner.disco.front.consts.Const;
import com.miner.disco.front.model.request.MerchantEvaluateListRequest;
import com.miner.disco.front.model.response.MerchantEvaluateListResponse;
import com.miner.disco.front.service.MerchantEvaluateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Created by lubycoder@163.com 2018/12/28
 */
@RestController
public class MerchantEvaluateController {

    @Autowired
    private MerchantEvaluateService merchantEvaluateService;

    @GetMapping(value = "/merchant/evaluate/list", headers = Const.API_VERSION_1_0_0)
    public ViewData list(MerchantEvaluateListRequest request) {
        List<MerchantEvaluateListResponse> responses = merchantEvaluateService.list(request);
        return ViewData.builder().data(responses).message("评论列表").build();
    }

}

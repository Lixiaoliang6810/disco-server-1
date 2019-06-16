package com.miner.disco.admin.controller;

import com.miner.disco.admin.auth.annotation.Func;
import com.miner.disco.admin.auth.annotation.Module;
import com.miner.disco.admin.constant.function.Review;
import com.miner.disco.admin.constant.module.merchant.SettlementApplyManager;
import com.miner.disco.admin.model.LoginModule;
import com.miner.disco.admin.model.request.merchant.SettlementApplyReviewRequest;
import com.miner.disco.admin.model.request.merchant.SettlementApplySearchRequest;
import com.miner.disco.admin.service.merchant.SettlementApplyService;
import com.miner.disco.basic.model.response.PageResponse;
import com.miner.disco.basic.model.response.ViewData;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * author:linjw Date:2019/2/21 Time:14:17
 */
@RestController
@RequestMapping(value = "/merchant/settlement/apply")
@Module(SettlementApplyManager.class)
public class SettlementApplyController {

    @Autowired
    private SettlementApplyService settlementApplyService;

    @RequestMapping(value = "/list")
    public ViewData list(SettlementApplySearchRequest searchRequest) {
        PageResponse pageResponse = settlementApplyService.list(searchRequest);
        return ViewData.builder().data(pageResponse.getList()).total(pageResponse.getTotal()).message("结算申请列表").build();
    }

    @PostMapping(value = "/auditing")
    @Func(Review.class)
    public ViewData auditing(SettlementApplyReviewRequest reviewRequest) {
        Subject subject = SecurityUtils.getSubject();
        LoginModule loginModule = (LoginModule) subject.getPrincipal();
        settlementApplyService.auditing(loginModule.getId(), reviewRequest);
        return ViewData.builder().message("审核成功").build();
    }
}

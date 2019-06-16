package com.miner.disco.admin.controller;

import com.miner.disco.admin.auth.annotation.Func;
import com.miner.disco.admin.auth.annotation.Module;
import com.miner.disco.admin.constant.function.Review;
import com.miner.disco.admin.constant.module.member.MemberManager;
import com.miner.disco.admin.model.LoginModule;
import com.miner.disco.admin.model.request.member.VipApplyAuditingRequest;
import com.miner.disco.admin.model.request.member.VipApplySearchRequest;
import com.miner.disco.admin.model.response.member.VipApplyDetailsResponse;
import com.miner.disco.admin.service.member.VipApplyService;
import com.miner.disco.basic.model.response.PageResponse;
import com.miner.disco.basic.model.response.ViewData;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 用户vip申请管理 author:linjw Date:2019/1/7 Time:18:04
 */
@RestController
@RequestMapping("/member")
@Module(MemberManager.class)
public class VipApplyController {

    @Autowired
    private VipApplyService vipApplyService;

    @GetMapping(value = "/vip/list")
    public ViewData list(VipApplySearchRequest searchRequest) {
        PageResponse response = vipApplyService.list(searchRequest);
        return ViewData.builder().data(response).message("vip申请列表").build();
    }

    @GetMapping("/vip/details")
    public ViewData details(@RequestParam("id") Long id) {
        VipApplyDetailsResponse details = vipApplyService.details(id);
        return ViewData.builder().data(details).message("申请详情").build();
    }

    @PutMapping(value = "/vip/review")
    @Func(Review.class)
    public ViewData review(@RequestBody VipApplyAuditingRequest auditingRequest) {
        Subject subject = SecurityUtils.getSubject();
        LoginModule loginModule = (LoginModule) subject.getPrincipal();
        vipApplyService.review(loginModule.getId(), auditingRequest);
        return ViewData.builder().message("审核完成").build();
    }

}

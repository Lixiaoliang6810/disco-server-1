package com.miner.disco.admin.controller;

import com.miner.disco.admin.auth.annotation.Func;
import com.miner.disco.admin.auth.annotation.Module;
import com.miner.disco.admin.constant.function.Edit;
import com.miner.disco.admin.constant.module.member.MemberManager;
import com.miner.disco.admin.model.request.member.MemberBillSearchRequest;
import com.miner.disco.admin.model.request.member.MemberOrderPageRequest;
import com.miner.disco.admin.model.request.member.MemberSearchRequest;
import com.miner.disco.admin.model.response.member.MemberDetailResponse;
import com.miner.disco.admin.service.member.MemberBillService;
import com.miner.disco.admin.service.member.MemberOrderService;
import com.miner.disco.admin.service.member.MemberService;
import com.miner.disco.basic.model.response.PageResponse;
import com.miner.disco.basic.model.response.ViewData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户管理
 * author:linjw Date:2019/1/4 Time:11:11
 */
@RestController
@Module(MemberManager.class)
public class MemberController {

    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberBillService memberBillService;

    @Autowired
    private MemberOrderService memberOrderService;

    @GetMapping(value = "/member/list")
    public ViewData list(MemberSearchRequest searchRequest){
        PageResponse pageResponse = memberService.queryMemberList(searchRequest);
        return ViewData.builder().data(pageResponse).message("会员列表").build();
    }

    @GetMapping("/member/details")
    public ViewData details(@RequestParam("id") Long id){
        MemberDetailResponse details = memberService.details(id);
        return ViewData.builder().data(details).message("用户详情").build();
    }

    @GetMapping(value = "/member/integral/list")
    public ViewData queryIntegralBillList(){
        return ViewData.builder().message("积分账单列表").build();
    }

    @GetMapping(value = "/member/bill/list")
    public ViewData list(MemberBillSearchRequest searchRequest) {
        PageResponse pageResponse = memberBillService.memberBillList(searchRequest);
        return ViewData.builder().data(pageResponse).message("用户账单列表").build();
    }

    @GetMapping(value = "/member/order/list")
    public ViewData orderList(MemberOrderPageRequest searchRequest) {
        PageResponse pageResponse = memberOrderService.findPage(searchRequest);
        return ViewData.builder().data(pageResponse).message("用户订单列表").build();
    }

    @PutMapping(value = "/member/recommend")
    @Func(Edit.class)
    public ViewData setRecommend(@RequestParam("id") Long id, @RequestParam("recommend") Integer recommend) {
        memberService.updateRecommend(id, recommend);
        return ViewData.builder().message("设置成功").build();
    }

    @PutMapping(value = "/member/leader")
    @Func(Edit.class)
    public ViewData setLeader(@RequestParam("id") Long id) {
        memberService.updateLeader(id);
        return ViewData.builder().message("设置成功").build();
    }
}

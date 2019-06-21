package com.miner.disco.front.controller;

import com.miner.disco.basic.model.response.ViewData;
import com.miner.disco.front.consts.Const;
import com.miner.disco.front.model.request.MemberInfoModifyRequest;
import com.miner.disco.front.model.request.MemberRegisterRequest;
import com.miner.disco.front.model.request.VipMemberListRequest;
import com.miner.disco.front.model.response.MemberMeCenterResponse;
import com.miner.disco.front.model.response.MemberTaCenterResponse;
import com.miner.disco.front.model.response.VipMemberListResponse;
import com.miner.disco.front.oauth.model.CustomUserDetails;
import com.miner.disco.front.service.MemberService;
import com.miner.disco.front.service.ShieldService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Created by lubycoder@163.com 2018/12/24
 */
@RestController
public class MemberController {

    @Autowired
    private MemberService memberService;

    @Autowired
    private ShieldService shieldService;

    @PostMapping(value = "/member/register", headers = Const.API_VERSION_1_0_0)
    public ViewData register(MemberRegisterRequest request) {
        request.setValidate(true);
        memberService.register(request);
        return ViewData.builder().message("注册成功").build();
    }

    @GetMapping(value = "/member/me/center", headers = Const.API_VERSION_1_0_0)
    public ViewData center(@AuthenticationPrincipal OAuth2Authentication oAuth2Authentication) {
        Long uid = ((CustomUserDetails) oAuth2Authentication.getPrincipal()).getId();
        MemberMeCenterResponse response = memberService.meCenter(uid);
        return ViewData.builder().data(response).message("我的主页").build();
    }

    @GetMapping(value = "/member/ta/center", headers = Const.API_VERSION_1_0_0)
    public ViewData homepage(@AuthenticationPrincipal OAuth2Authentication oAuth2Authentication,
                             @RequestParam("userId") Long userId) {
        Long currentUserId = 0L;
        if(oAuth2Authentication!=null){
            currentUserId = ((CustomUserDetails) oAuth2Authentication.getPrincipal()).getId();
        }
        MemberTaCenterResponse response = memberService.taCenter(currentUserId, userId);
        return ViewData.builder().data(response).message("他的主页").build();
    }

    @PostMapping(value = "/member/login/password/reset", headers = Const.API_VERSION_1_0_0)
    public ViewData resetLoginPassword(@RequestParam("mobile") String mobile,
                                       @RequestParam("digit") String digit,
                                       @RequestParam("password") String password) {
        memberService.resetLoginPassword(mobile, digit, password);
        return ViewData.builder().message("重置成功").build();
    }

    @PostMapping(value = "/member/info/modify", headers = Const.API_VERSION_1_0_0)
    public ViewData modify(@AuthenticationPrincipal OAuth2Authentication oAuth2Authentication,
                           MemberInfoModifyRequest request) {
        Long uid = ((CustomUserDetails) oAuth2Authentication.getPrincipal()).getId();
        request.setId(uid);
        memberService.modifyInfo(request);
        return ViewData.builder().message("修改成功").build();
    }

    @GetMapping(value = "/member/vip/list", headers = Const.API_VERSION_1_0_0)
    public ViewData vips(@AuthenticationPrincipal OAuth2Authentication oAuth2Authentication,VipMemberListRequest request){
        if(oAuth2Authentication==null){
            List<VipMemberListResponse> vips = vips(request);
            return ViewData.builder().data(vips).message("玩家列表").build();
        }
        // 获取附近广场玩家
        List<VipMemberListResponse> vips =vips(request);

        Long currentUserId = ((CustomUserDetails) oAuth2Authentication.getPrincipal()).getId();
        // 筛除当前用户已屏蔽的玩家
        List<VipMemberListResponse> screenedVipList = shieldService.screenList(currentUserId, vips);
        return ViewData.builder().data(screenedVipList).message("玩家列表").build();
    }

    private List<VipMemberListResponse> vips(VipMemberListRequest request) {
        return memberService.vips(request);
    }

    @GetMapping(value = "/member/chat/session")
    public ViewData chatSession(@RequestParam("imAccount") String imAccount) {
        Long id = memberService.chatSession(imAccount);
        return ViewData.builder().data(id).build();
    }


}

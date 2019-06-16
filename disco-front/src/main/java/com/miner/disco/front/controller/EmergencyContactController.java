package com.miner.disco.front.controller;

import com.miner.disco.basic.model.response.ViewData;
import com.miner.disco.front.consts.Const;
import com.miner.disco.front.model.response.EmergencyContactListResponse;
import com.miner.disco.front.oauth.model.CustomUserDetails;
import com.miner.disco.front.service.EmergencyContactService;
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
public class EmergencyContactController {

    @Autowired
    private EmergencyContactService emergencyContactService;

    @PostMapping(value = "/member/emergency/contact/create", headers = Const.API_VERSION_1_0_0)
    public ViewData create(@AuthenticationPrincipal OAuth2Authentication oAuth2Authentication,
                           @RequestParam("realname") String realname,
                           @RequestParam("mobile") String mobile) {
        Long uid = ((CustomUserDetails) oAuth2Authentication.getPrincipal()).getId();
        emergencyContactService.create(uid, realname, mobile);
        return ViewData.builder().message("添加成功").build();
    }

    @PostMapping(value = "/member/emergency/contact/delete", headers = Const.API_VERSION_1_0_0)
    public ViewData delete(@AuthenticationPrincipal OAuth2Authentication oAuth2Authentication,
                           @RequestParam("id") Long id) {
        Long uid = ((CustomUserDetails) oAuth2Authentication.getPrincipal()).getId();
        emergencyContactService.delete(uid, id);
        return ViewData.builder().message("删除成功").build();
    }

    @GetMapping(value = "/member/emergency/contact/list", headers = Const.API_VERSION_1_0_0)
    public ViewData list(@AuthenticationPrincipal OAuth2Authentication oAuth2Authentication) {
        Long uid = ((CustomUserDetails) oAuth2Authentication.getPrincipal()).getId();
        List<EmergencyContactListResponse> responses = emergencyContactService.list(uid);
        return ViewData.builder().data(responses).message("紧急联系人列表").build();
    }

}

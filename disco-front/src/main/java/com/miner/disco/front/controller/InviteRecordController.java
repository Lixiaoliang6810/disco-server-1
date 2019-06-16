package com.miner.disco.front.controller;

import com.miner.disco.basic.model.response.ViewData;
import com.miner.disco.front.consts.Const;
import com.miner.disco.front.model.request.InviteRecordListRequest;
import com.miner.disco.front.model.response.InviteRecordListResponse;
import com.miner.disco.front.oauth.model.CustomUserDetails;
import com.miner.disco.front.service.InviteRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Created by lubycoder@163.com 2019/1/16
 */
@RestController
public class InviteRecordController {

    @Autowired
    private InviteRecordService inviteRecordService;

    @GetMapping(value = "/invite/record", headers = Const.API_VERSION_1_0_0)
    public ViewData list(@AuthenticationPrincipal OAuth2Authentication oAuth2Authentication,
                         InviteRecordListRequest request) {
        Long uid = ((CustomUserDetails) oAuth2Authentication.getPrincipal()).getId();
        request.setUserId(uid);
        List<InviteRecordListResponse> response = inviteRecordService.list(request);
        return ViewData.builder().data(response).message("邀请列表").build();
    }

}

package com.miner.disco.front.controller;

import com.miner.disco.basic.model.response.ViewData;
import com.miner.disco.front.consts.Const;
import com.miner.disco.front.model.request.FriendListRequest;
import com.miner.disco.front.model.response.FriendListResponse;
import com.miner.disco.front.oauth.model.CustomUserDetails;
import com.miner.disco.front.service.FriendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Created by lubycoder@163.com 2019/1/3
 */
@RestController
public class FriendController {

    @Autowired
    private FriendService friendService;

    @PostMapping(value = "/friend/add", headers = Const.API_VERSION_1_0_0)
    public ViewData add(@AuthenticationPrincipal OAuth2Authentication oAuth2Authentication,
                        @RequestParam("himUserId") Long himUserId) {
        Long uid = ((CustomUserDetails) oAuth2Authentication.getPrincipal()).getId();
        friendService.add(uid, himUserId);
        return ViewData.builder().message("添加成功").build();
    }

    @PostMapping(value = "/friend/remove", headers = Const.API_VERSION_1_0_0)
    public ViewData remove(@AuthenticationPrincipal OAuth2Authentication oAuth2Authentication,
                        @RequestParam("himUserId") Long himUserId) {
        Long uid = ((CustomUserDetails) oAuth2Authentication.getPrincipal()).getId();
        friendService.remove(uid, himUserId);
        return ViewData.builder().message("删除成功").build();
    }

    @GetMapping(value = "/friend/list", headers = Const.API_VERSION_1_0_0)
    public ViewData list(@AuthenticationPrincipal OAuth2Authentication oAuth2Authentication,
                         FriendListRequest request) {
        Long uid = ((CustomUserDetails) oAuth2Authentication.getPrincipal()).getId();
        request.setUserId(uid);
        List<FriendListResponse> responses = friendService.list(request);
        return ViewData.builder().data(responses).message("好友列表").build();
    }

}

package com.miner.disco.front.controller;

import com.miner.disco.basic.model.response.ViewData;
import com.miner.disco.front.model.request.FollowListRequest;
import com.miner.disco.front.model.response.FollowListResponse;
import com.miner.disco.front.oauth.model.CustomUserDetails;
import com.miner.disco.front.service.FollowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Created by lubycoder@163.com 2018/12/24
 */
@RestController
public class FollowController {

//    @Autowired
//    private FollowService followService;
//
//    @PostMapping(value = "/member/follow")
//    public ViewData follow(@AuthenticationPrincipal CustomUserDetails userDetails,
//                           @RequestParam("idolId") Long idolId) {
//        followService.follow(idolId, userDetails.getId());
//        return ViewData.builder().message("关注成功").build();
//    }
//
//    @PostMapping(value = "/member/unfollow",consumes = MediaType.APPLICATION_JSON_VALUE)
//    public ViewData unfollow(@AuthenticationPrincipal CustomUserDetails userDetails,
//                             @RequestParam("idolId") Long idolId) {
//        followService.unfollow(idolId, userDetails.getId());
//        return ViewData.builder().message("取关成功").build();
//    }
//
//    @GetMapping(value = "/member/follow/list")
//    public ViewData list(@AuthenticationPrincipal CustomUserDetails userDetails,
//                         FollowListRequest request) {
//        request.setUserId(userDetails.getId());
//        List<FollowListResponse> responses = followService.list(request);
//        return ViewData.builder().data(responses).message("关注列表").build();
//    }

}

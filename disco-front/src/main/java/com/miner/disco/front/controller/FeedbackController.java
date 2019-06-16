package com.miner.disco.front.controller;

import com.miner.disco.basic.model.response.ViewData;
import com.miner.disco.front.consts.Const;
import com.miner.disco.front.model.request.FeedbackRequest;
import com.miner.disco.front.oauth.model.CustomUserDetails;
import com.miner.disco.front.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Created by lubycoder@163.com 2019-2-14
 */
@RestController
public class FeedbackController {

    @Autowired
    private FeedbackService feedbackService;

    @PostMapping(value = "/feedback", headers = Const.API_VERSION_1_0_0)
    public ViewData feedback(@AuthenticationPrincipal OAuth2Authentication oAuth2Authentication,
                             FeedbackRequest request) {
        Long uid = ((CustomUserDetails) oAuth2Authentication.getPrincipal()).getId();
        request.setUserId(uid);
        feedbackService.feedback(request);
        return ViewData.builder().message("反馈成功").build();
    }

}

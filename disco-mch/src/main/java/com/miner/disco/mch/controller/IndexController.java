package com.miner.disco.mch.controller;

import com.miner.disco.basic.model.response.ViewData;
import com.miner.disco.mch.consts.Const;
import com.miner.disco.mch.model.response.IndexResponse;
import com.miner.disco.mch.oauth.model.CustomUserDetails;
import com.miner.disco.mch.service.IndexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

/**
 * @author Created by lubycoder@163.com 2019/1/9
 */
@RestController
public class IndexController {

    @Autowired
    private IndexService indexService;

    @GetMapping(value = "/index", headers = Const.API_VERSION_1_0_0)
    public ViewData index(@AuthenticationPrincipal OAuth2Authentication oAuth2Authentication) {
        Long merchantId = ((CustomUserDetails) oAuth2Authentication.getPrincipal()).getId();
        IndexResponse indexResponse = indexService.index(merchantId);
        return ViewData.builder().data(indexResponse).message("首页").build();
    }

}

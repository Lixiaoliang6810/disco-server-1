package com.miner.disco.front.controller;

import com.miner.disco.basic.model.response.ViewData;
import com.miner.disco.front.consts.Const;
import com.miner.disco.front.model.request.ShieldRequest;
import com.miner.disco.front.oauth.model.CustomUserDetails;
import com.miner.disco.front.service.ShieldService;
import com.miner.disco.front.service.impl.ShieldServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName ShieldController
 * @Author: wz1016_vip@163.com
 * @Date: 2019/6/17 16:10
 * @Description: TODO
 */
@RestController
public class ShieldController {

    private ShieldServiceImpl shieldService;

    @Autowired
    public ShieldController(ShieldServiceImpl shieldService){
        this.shieldService = shieldService;
    }

    /**
     * 屏蔽用户
     *
     * @param oAuth2Authentication oAuth
     * @param request 被屏蔽人的信息
     */
    @PostMapping(value = "/member/vip/shield", headers = Const.API_VERSION_1_0_0)
    public ViewData shield(@AuthenticationPrincipal OAuth2Authentication oAuth2Authentication, ShieldRequest request){
        Long currentUserId = ((CustomUserDetails) oAuth2Authentication.getPrincipal()).getId();
        assert request.getId()!=null;
//        request.setId(1247L);
        if(currentUserId.intValue() == request.getId().intValue()){
            return ViewData.builder().message("不能屏蔽自己").build();
        }

        // 获取当前用户屏蔽的用户id
        shieldService.shield(currentUserId,request);
        return ViewData.builder().message("屏蔽成功").build();
    }
}

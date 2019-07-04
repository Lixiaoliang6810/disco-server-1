package com.miner.disco.front.controller;

import com.miner.disco.basic.model.response.ViewData;
import com.miner.disco.front.consts.Const;
import com.miner.disco.front.service.RefundService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: wz1016_vip@163.com  2019/7/3
 */
@Slf4j
@RestController
public class RefundController {

    private final RefundService refundService;

    @Autowired
    public RefundController(RefundService wxOrdersService){
        this.refundService = wxOrdersService;
    }

    /**
     * 退款申请
     * @return
     */
    @PostMapping(value = "/orders/refund/apply", headers = Const.API_VERSION_1_0_0)
    public ViewData refund(@AuthenticationPrincipal OAuth2Authentication oAuth2Authentication,
                           @RequestParam("outTradeNo") String outTradeNo){
        return null;
    }


}

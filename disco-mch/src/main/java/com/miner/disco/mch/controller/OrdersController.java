package com.miner.disco.mch.controller;

import com.miner.disco.basic.model.response.ViewData;
import com.miner.disco.mch.consts.Const;
import com.miner.disco.mch.model.request.OrdersListRequest;
import com.miner.disco.mch.model.response.OrdersDetailsResponse;
import com.miner.disco.mch.model.response.OrdersListResponse;
import com.miner.disco.mch.oauth.model.CustomUserDetails;
import com.miner.disco.mch.service.OrdersService;
import com.miner.disco.pojo.Orders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

/**
 * @author Created by lubycoder@163.com 2019/1/9
 */
@RestController
public class OrdersController {

    @Autowired
    private OrdersService ordersService;

    @GetMapping(value = "/merchant/orders/list", headers = Const.API_VERSION_1_0_0)
    public ViewData list(@AuthenticationPrincipal OAuth2Authentication oAuth2Authentication,
                         OrdersListRequest request) {
        Long merchantId = ((CustomUserDetails) oAuth2Authentication.getPrincipal()).getId();
        request.setMerchantId(merchantId);
        request.setStatus(Orders.STATUS.WAIT_CONSUMPTION.getKey());
        List<OrdersListResponse> response = ordersService.list(request);
        return ViewData.builder().data(response).message("订单列表").build();
    }

    @GetMapping(value = "/merchant/orders/details", headers = Const.API_VERSION_1_0_0)
    public ViewData details(@RequestParam("ordersId") Long ordersId) {
        OrdersDetailsResponse response = ordersService.details(ordersId);
        return ViewData.builder().data(response).message("订单详情").build();
    }

    @PostMapping(value = "/merchant/orders/confirm")
    public ViewData confirm(@AuthenticationPrincipal OAuth2Authentication oAuth2Authentication,
                            @RequestParam("ordersId") Long ordersId) {
        Long merchantId = ((CustomUserDetails) oAuth2Authentication.getPrincipal()).getId();
        ordersService.confirm(merchantId, ordersId);
        return ViewData.builder().build();
    }

}

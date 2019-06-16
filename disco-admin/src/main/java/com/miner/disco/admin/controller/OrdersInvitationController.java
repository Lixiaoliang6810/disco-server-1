package com.miner.disco.admin.controller;

import com.miner.disco.admin.model.request.orders.InvitationSearchRequest;
import com.miner.disco.admin.service.orders.OrdersInvitationService;
import com.miner.disco.basic.model.response.PageResponse;
import com.miner.disco.basic.model.response.ViewData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 订单邀约管理
 * author:linjw Date:2019/1/7 Time:18:53
 */
@RestController
public class OrdersInvitationController {

    @Autowired
    private OrdersInvitationService ordersInvitationService;

    @GetMapping(value = "/orders/invitation/list")
    public ViewData InvitationList(InvitationSearchRequest search) {
        PageResponse pageResponse = ordersInvitationService.InvitationList(search);
        return ViewData.builder().data(pageResponse.getList()).total(pageResponse.getTotal()).message("约单列表").build();
    }

}

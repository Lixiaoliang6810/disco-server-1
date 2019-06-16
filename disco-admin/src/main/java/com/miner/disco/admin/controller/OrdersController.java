package com.miner.disco.admin.controller;

import com.miner.disco.admin.model.request.orders.OrdersSearchRequest;
import com.miner.disco.admin.model.response.orders.OrdersDetailResponse;
import com.miner.disco.admin.service.orders.OrdersService;
import com.miner.disco.basic.model.response.PageResponse;
import com.miner.disco.basic.model.response.ViewData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 订单管理
 * author:linjw Date:2019/1/7 Time:15:30
 */
@RestController
public class OrdersController {

    @Autowired
    private OrdersService ordersService;

    @GetMapping(value = "/orders/list")
    public ViewData ordersList(OrdersSearchRequest searchRequest) {
        PageResponse pageResponse = ordersService.findPage(searchRequest);
        return ViewData.builder().data(pageResponse.getList()).total(pageResponse.getTotal()).message("订单列表").build();
    }

    @GetMapping(value = "/orders/detail")
    public ViewData ordersDetail(Long id){
        OrdersDetailResponse detailResponse = ordersService.detail(id);
        return ViewData.builder().data(detailResponse).message("订单详情").build();
    }
}

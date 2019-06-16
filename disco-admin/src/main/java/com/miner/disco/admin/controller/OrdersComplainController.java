package com.miner.disco.admin.controller;

import com.miner.disco.admin.service.orders.OrdersComplainService;
import com.miner.disco.basic.model.request.Pagination;
import com.miner.disco.basic.model.response.PageResponse;
import com.miner.disco.basic.model.response.ViewData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 订单投诉管理
 * author:linjw Date:2019/1/7 Time:16:01
 */
@RestController
public class OrdersComplainController {

    @Autowired
    private OrdersComplainService complainService;

    public ViewData complainList(@RequestParam(value = "no", required = false) String no, Pagination page) {
        PageResponse pageResponse = complainService.complainList(page, no);
        return ViewData.builder().data(pageResponse.getList()).total(pageResponse.getTotal()).message("投诉列表").build();
    }
}

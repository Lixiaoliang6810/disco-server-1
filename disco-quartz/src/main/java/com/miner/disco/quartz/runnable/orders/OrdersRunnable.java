package com.miner.disco.quartz.runnable.orders;

import com.miner.disco.quartz.service.orders.OrdersService;

/**
 * author:linjw Date:2019/1/14 Time:15:03
 */
public class OrdersRunnable implements Runnable {


    private OrdersService ordersService;

    private Long ordersId;

    @Override
    public void run() {
        ordersService.ordersOvertime(ordersId);
    }

    public OrdersRunnable(OrdersService ordersService, Long ordersId) {
        this.ordersService = ordersService;
        this.ordersId = ordersId;
    }

}

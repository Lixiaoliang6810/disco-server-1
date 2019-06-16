package com.miner.disco.quartz.service.orders;

import com.miner.disco.quartz.exception.QuartzBuzException;

/**
 * author:linjw Date:2019/1/14 Time:11:44
 */
public interface OrdersService {
    void ordersOvertime(Long id) throws QuartzBuzException;
}

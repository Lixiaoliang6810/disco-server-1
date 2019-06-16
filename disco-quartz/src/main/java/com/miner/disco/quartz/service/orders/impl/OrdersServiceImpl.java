package com.miner.disco.quartz.service.orders.impl;

import com.miner.disco.basic.assertion.Assert;
import com.miner.disco.pojo.Orders;
import com.miner.disco.pojo.Orders.STATUS;
import com.miner.disco.quartz.dao.orders.OrdersDao;
import com.miner.disco.quartz.exception.QuartzBuzException;
import com.miner.disco.quartz.exception.QuartzBuzExceptionCode;
import com.miner.disco.quartz.service.orders.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * author:linjw Date:2019/1/14 Time:11:44
 */
@Service
public class OrdersServiceImpl implements OrdersService {

    @Autowired
    private OrdersDao ordersDao;

    @Override
    public void ordersOvertime(Long id) throws QuartzBuzException {
        Orders orders = ordersDao.queryByPrimaryKey(id);
        Assert.notNull(orders, QuartzBuzExceptionCode.OBJECT_NOT_EXIST.getCode());
        Assert.isTrue(orders.getStatus().intValue() == STATUS.WAIT_PAYMENT.getKey().intValue(), QuartzBuzExceptionCode.ORDERS_STATUS_ERROR.getCode());
        orders.setStatus(STATUS.PAYMENT_TIMEOUT.getKey());
        ordersDao.updateByPrimaryKey(orders);
    }
}

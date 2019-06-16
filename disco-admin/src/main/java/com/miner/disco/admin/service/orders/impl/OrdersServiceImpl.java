package com.miner.disco.admin.service.orders.impl;

import com.miner.disco.admin.dao.orders.OrdersDao;
import com.miner.disco.admin.exception.AdminBuzException;
import com.miner.disco.admin.model.request.orders.OrdersSearchRequest;
import com.miner.disco.admin.model.response.orders.OrdersDetailResponse;
import com.miner.disco.admin.model.response.orders.OrdersListResponse;
import com.miner.disco.admin.service.orders.OrdersService;
import com.miner.disco.basic.model.response.PageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * author:linjw Date:2019/1/7 Time:15:26
 */
@Service
public class OrdersServiceImpl implements OrdersService {

    @Autowired
    private OrdersDao ordersDao;

    @Override
    public PageResponse findPage(OrdersSearchRequest search) throws AdminBuzException {
        List<OrdersListResponse> listResponseList = ordersDao.queryPage(search);
        int total = ordersDao.queryPageCount(search);
        return new PageResponse(total, listResponseList);
    }

    @Override
    public OrdersDetailResponse detail(Long id) throws AdminBuzException {
        return ordersDao.detail(id);
    }
}

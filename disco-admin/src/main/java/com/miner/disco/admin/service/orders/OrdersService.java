package com.miner.disco.admin.service.orders;

import com.miner.disco.admin.exception.AdminBuzException;
import com.miner.disco.admin.model.request.orders.OrdersSearchRequest;
import com.miner.disco.admin.model.response.orders.OrdersDetailResponse;
import com.miner.disco.basic.model.response.PageResponse;

/**
 * author:linjw Date:2019/1/7 Time:15:26
 */
public interface OrdersService {

    /**
     * 订单列表
     * @param search
     * @return
     * @throws AdminBuzException
     */
    PageResponse findPage(OrdersSearchRequest search) throws AdminBuzException;

    /**
     * 订单详情
     * @param id
     * @return
     */
    OrdersDetailResponse detail(Long id) throws AdminBuzException;
}

package com.miner.disco.mch.service;

import com.miner.disco.mch.exception.MchBusinessException;
import com.miner.disco.mch.model.request.OrdersListRequest;
import com.miner.disco.mch.model.response.OrdersDetailsResponse;
import com.miner.disco.mch.model.response.OrdersListResponse;

import java.util.List;

/**
 * @author Created by lubycoder@163.com 2019/1/9
 */
public interface OrdersService {

    List<OrdersListResponse> list(OrdersListRequest request) throws MchBusinessException;

    OrdersDetailsResponse details(Long ordersId) throws MchBusinessException;

    void confirm(Long merchantId, Long ordersId) throws MchBusinessException;

}

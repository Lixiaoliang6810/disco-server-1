package com.miner.disco.mch.dao;

import com.miner.disco.mch.model.request.OrdersListRequest;
import com.miner.disco.mch.model.response.OrdersDetailsResponse;
import com.miner.disco.mch.model.response.OrdersListResponse;
import com.miner.disco.mybatis.support.BasicMapper;
import com.miner.disco.pojo.Orders;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Created by lubycoder@163.com 2019/1/9
 */
public interface OrdersMapper extends BasicMapper<Orders> {

    List<OrdersListResponse> queryByMerchantId(OrdersListRequest request);

    OrdersDetailsResponse queryById(@Param("ordersId") Long id);

    Integer statisticsToDayOrders(@Param("merchantId") Long merchantId);

    Integer statisticsThisMonthOrders(@Param("merchantId") Long merchantId);

    Integer statisticsTotalOrders(@Param("merchantId") Long merchantId);

}

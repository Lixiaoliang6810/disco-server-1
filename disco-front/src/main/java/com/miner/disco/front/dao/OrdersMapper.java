package com.miner.disco.front.dao;

import com.miner.disco.front.model.request.ApplyAssembleOrdersListRequest;
import com.miner.disco.front.model.request.AssembleOrdersListRequest;
import com.miner.disco.front.model.request.LaunchAssembleOrdersListRequest;
import com.miner.disco.front.model.request.OrdersListRequest;
import com.miner.disco.front.model.response.*;
import com.miner.disco.pojo.Orders;
import com.miner.disco.mybatis.support.BasicMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Created by lubycoder@163.com 2018/12/24
 */
public interface OrdersMapper extends BasicMapper<Orders> {

    List<OrdersListResponse> queryByUserId(OrdersListRequest request);

    OrdersDetailsResponse queryById(@Param("ordersId") Long ordersId);

    Orders queryByPrimaryKeyForUpdate(@Param("ordersId") Long ordersId);

    List<AssembleOrdersListResponse> queryAssembleList(AssembleOrdersListRequest request);

    List<LaunchAssembleOrdersListResponse> queryLaunchAssembleList(LaunchAssembleOrdersListRequest request);

    List<ApplyAssembleOrdersListResponse> queryApplyAssembleList(ApplyAssembleOrdersListRequest request);

}

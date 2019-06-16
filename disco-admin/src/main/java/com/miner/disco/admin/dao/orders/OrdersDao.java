package com.miner.disco.admin.dao.orders;

import com.miner.disco.admin.model.request.member.MemberOrderPageRequest;
import com.miner.disco.admin.model.request.orders.OrdersSearchRequest;
import com.miner.disco.admin.model.response.member.MemberOrderPageResponse;
import com.miner.disco.admin.model.response.orders.OrdersDetailResponse;
import com.miner.disco.admin.model.response.orders.OrdersListResponse;
import com.miner.disco.mybatis.support.BasicMapper;
import com.miner.disco.pojo.Orders;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * author:linjw Date:2019/1/7 Time:14:54
 */
public interface OrdersDao extends BasicMapper<Orders> {

    List<OrdersListResponse> queryPage(OrdersSearchRequest search);

    int queryPageCount(OrdersSearchRequest search);

    OrdersDetailResponse detail(@Param("id") Long id);

    /**
     * 用户订单列表
     * @return
     */
    List<MemberOrderPageResponse> queryMemberOrderPage(@Param("search") MemberOrderPageRequest search);

    Integer queryMemberOrderPageCount(@Param("search") MemberOrderPageRequest search);
}

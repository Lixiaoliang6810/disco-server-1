package com.miner.disco.front.dao;

import com.miner.disco.front.model.response.OrdersInvitationResponse;
import com.miner.disco.pojo.OrdersInvitation;
import com.miner.disco.mybatis.support.BasicMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Created by lubycoder@163.com 2018/12/24
 */
public interface OrdersInvitationMapper extends BasicMapper<OrdersInvitation> {

    List<OrdersInvitationResponse> queryJoinedByOrdersId(@Param("ordersId") Long ordersId, @Param("status") String status);


    OrdersInvitation queryJoinedByUserIdAndOrdersId(@Param("userId") Long userId, @Param("ordersId") Long ordersId);

}

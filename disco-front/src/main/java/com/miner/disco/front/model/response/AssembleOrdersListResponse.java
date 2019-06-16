package com.miner.disco.front.model.response;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * @author Created by lubycoder@163.com 2019-3-6
 */
@Getter
@Setter
public class AssembleOrdersListResponse implements Serializable {

    private static final long serialVersionUID = 4360208193285125014L;

    /**
     * 订单主键
     */
    private Long ordersId;
    /**
     * 商户名称
     */
    private String merchantName;
    /**
     * 拼座位数
     */
    private Integer assembleSeatsCount;
    /**
     * 剩余座位
     */
    private Integer assembleSeatsSurplus;
    /**
     * 拼座成员
     */
    List<OrdersInvitationResponse> assembleMembers;

}

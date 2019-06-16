package com.miner.disco.front.model.response;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author Created by lubycoder@163.com 2019-3-6
 */
@Getter
@Setter
public class OrdersAssembleDetailsResponse implements Serializable {

    private static final long serialVersionUID = -6806067014885219797L;

    /**
     * 订单主键
     */
    private Long ordersId;
    /**
     * 订单号
     */
    private String no;
    /**
     * 订单状态
     */
    private Integer status;
    /**
     * 到店时间
     */
    private Date arrivalTime;
    /**
     * 商家名称
     */
    private String merchantName;
    /**
     * 商户LOGO
     */
    private String logo;
    /**
     * 标签(多个标签使用逗号拆分)
     */
    private String label;
    /**
     * 商家地址
     */
    private String address;
    /**
     * 商家纬度
     */
    private Double latitude;
    /**
     * 商家经度
     */
    private Double longitude;
    /**
     * 商品名称
     */
    private String goodsName;
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
    private List<OrdersInvitationResponse> assembleMembers;

}

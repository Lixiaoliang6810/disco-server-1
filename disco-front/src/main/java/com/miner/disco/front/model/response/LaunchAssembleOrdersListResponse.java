package com.miner.disco.front.model.response;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author Created by lubycoder@163.com 2019-3-6
 */
@Getter
@Setter
public class LaunchAssembleOrdersListResponse implements Serializable {

    private static final long serialVersionUID = -5742026267756135123L;

    private Long ordersId;

    private String merchantName;

    private Integer status;

    private BigDecimal percapita;

    private Date arrivalTime;

    private String goodsName;

    private Integer assembleSeatsCount;

    private Integer assembleSeatsSurplus;

    private List<OrdersInvitationResponse> assembleMembers;

}

package com.miner.disco.front.model.response;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author Created by lubycoder@163.com 2019/1/7
 */
@Getter
@Setter
public class OrdersListResponse implements Serializable {

    private static final long serialVersionUID = 5401971062972106275L;

    private Long ordersId;
    private String no;
    private Integer status;
    private Date arrivalTime;
    private Integer number;
    private BigDecimal tailMoney;
    private BigDecimal totalMoney;
    private Date createDate;
    private String merchantName;
    private String goodsName;
    private Integer assembleSeats;
    private String address;
    private String logo;

}

package com.miner.disco.mch.model.response;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author Created by lubycoder@163.com 2019/1/9
 */
@Getter
@Setter
public class OrdersListResponse implements Serializable {

    private static final long serialVersionUID = -2201603570875551393L;

    private String no;
    private Long ordersId;
    private String goodsName;
    private String merchantName;
    private String fullname;
    private String mobile;
    private Integer number;
    private BigDecimal totalMoney;
    private String salutation;
    private Date arrivalTime;
    private Date createDate;
    private Date paymentDate;

}

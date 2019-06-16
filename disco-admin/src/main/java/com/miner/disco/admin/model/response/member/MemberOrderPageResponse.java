package com.miner.disco.admin.model.response.member;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author: chencx
 * @create: 2019-01-17
 **/
@Getter
@Setter
public class MemberOrderPageResponse implements Serializable {

    private static final long serialVersionUID = -1340452885203707203L;

    private Long id;

    /**
     * 订单号
     */
    private String no;
    /**
     * 订单类型
     */
    private Integer type;
    /**
     * 订单状态
     */
    private Integer status;
    /**
     * 到店时间
     */
    private Date arrivalTime;
    /**
     * 定金
     */
    private BigDecimal earnestMoney;
    /**
     * 尾款
     */
    private BigDecimal tailMoney;
    /**
     * 订单总价
     */
    private BigDecimal totalMoney;
    /**
     * 创建时间
     */
    private Date createDate;
    /**
     * 支付时间
     */
    private Date paymentDate;
    /**
     * 更新时间
     */
    private Date updateDate;
}

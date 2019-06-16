package com.miner.disco.admin.model.response.orders;

import java.math.BigDecimal;
import java.util.Date;
import lombok.Getter;
import lombok.Setter;

/**
 * author:linjw Date:2019/1/7 Time:14:56
 */
@Getter
@Setter
public class OrdersListResponse {

    /**
     * 主键
     */
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
     * 商品类型名称
     */
    private String typeName;
    /**
     * 订单状态
     */
    private Integer status;
    /**
     * 订单状态
     */
    private String statusName;
    /**
     * 买方主键
     */
    private Long buyer;
    /**
     * 买方名称
     */
    private String buyerName;
    /**
     * 卖方主键
     */
    private Long seller;
    /**
     * 卖方名称
     */
    private Long sellerName;
    /**
     *
     */
    private String salutation;
    /**
     * 订单总价
     */
    private BigDecimal totalMoney;
    /**
     * 商品主键
     */
    private Long goodsId;
    /**
     * 商品名称
     */
    private String goodsName;
    /**
     * 创建时间
     */
    private Date createDate;
}

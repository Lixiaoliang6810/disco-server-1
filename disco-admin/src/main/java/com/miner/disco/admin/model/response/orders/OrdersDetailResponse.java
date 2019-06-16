package com.miner.disco.admin.model.response.orders;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

/**
 * author:linjw Date:2019/1/7 Time:15:20
 */
@Getter
@Setter
public class OrdersDetailResponse {

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
     * 订单状态
     */
    private Integer status;
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
     * 到店时间
     */
    private Date arrivalTime;
    /**
     * 称呼
     */
    private String salutation;
    /**
     * 姓名
     */
    private String fullname;
    /**
     * 手机号
     */
    private String mobile;
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
     * 退款金额
     */
    private BigDecimal refundMoney;

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
    /**
     * 更新时间
     */
    private Date updateDate;
}

package com.miner.disco.mch.model.response;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author Created by lubycoder@163.com 2019-2-19
 */
@Getter
@Setter
public class OrdersDetailsResponse implements Serializable {

    private static final long serialVersionUID = -4395518509349902352L;

    /**
     * 订单主键
     */
    private Long ordersId;
    /**
     * 订单号
     */
    private String no;
    /**
     * 手机号
     */
    private String mobile;
    /**
     * 订单状态
     */
    private Integer status;
    /**
     * 客户昵称
     */
    private String fullname;
    /**
     * 称谓
     */
    private String salutation;
    /**
     * 到店时间
     */
    private Date arrivalTime;
    /**
     * 定金
     */
    private BigDecimal tailMoney;
    /**
     * 订单总金额
     */
    private BigDecimal totalMoney;
    /**
     * 退款金额
     */
    private BigDecimal refundMoney;
    /**
     * 创建时间
     */
    private Date createDate;
    /**
     * 支付时间
     */
    private Date paymentDate;
    /**
     * 退款时间
     */
    private Date refundDate;
    /**
     * 商家名称
     */
    private String merchantName;
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
     * 商家电话
     */
    private String tel;
    /**
     * 商品名称
     */
    private String goodsName;
    /**
     * 订单二维码
     */
    private String qrcode;
    /**
     * 预定人数
     */
    private Integer number;

}

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
public class OrdersDetailsResponse implements Serializable {

    private static final long serialVersionUID = -2461368689806494086L;

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
     * 商户LOGO
     */
    private String logo;
    /**
     * 订单二维码
     */
    private String qrcode;
    /**
     * 标签(多个标签使用逗号拆分)
     */
    private String label;
    /**
     * 是否拼座
     */
    private Integer assembleSeats;
    /**
     * 拼座位数
     */
    private Integer assembleSeatsCount;
    /**
     * 剩余座位
     */
    private Integer assembleSeatsSurplus;

    public String getQrcode() {
        return String.format("disco://orders?ordersId=%s&no=%s", ordersId, no);
    }
}

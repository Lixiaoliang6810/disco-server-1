package com.miner.disco.mch.model.response;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author Created by lubycoder@163.com 2019-2-20
 */
@Getter
@Setter
public class MerchantDetailsResponse implements Serializable {

    private static final long serialVersionUID = 7536721580726443550L;

    /**
     * 名称
     */
    private String name;
    /**
     * 地址
     */
    private String address;
    /**
     * 商户LOGO
     */
    private String logo;
    /**
     * 电话
     */
    private String tel;
    /**
     * 标签(多个标签使用逗号拆分)
     */
    private String label;
    /**
     * 轮播图
     */
    private String slideshow;
    /**
     * 人均消费
     */
    private BigDecimal percapita;
    /**
     * 商家类型主键
     */
    private Long classifyId;
    /**
     * 商家类型
     */
    private String classify;
    /**
     * 分数
     */
    private Double score;
    /**
     * 营业时间
     */
    private String officeHours;
    /**
     * 经度
     */
    private Double longitude;
    /**
     * 纬度
     */
    private Double latitude;
    /**
     * 商户状态
     */
    private Integer status;
    /**
     * 可用余额
     */
    private BigDecimal usableBalance;

}

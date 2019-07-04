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
public class MerchantListResponse implements Serializable {

    private static final long serialVersionUID = -8968871821797926555L;

    /**
     * 商家主键
     */
    private Long merchantId;
    /**
     * 名称
     */
    private String name;
    /**
     * 商家LOGO
     */
    private String logo;
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
     * 商家类型
     */
    private Integer type;
    /**
     * 分数
     */
    private Double score;
    /**
     * 商家地址
     */
    private String address;
    /**
     * 纬度
     */
    private Double latitude;
    /**
     * 经度
     */
    private Double longitude;
    /**
     * 距离
     */
    private Double distance;
    /**
     * 创建时间
     */
    private transient Date createDate;
    /**
     * 更新时间
     */
    private transient Date updateDate;
    /**
     * 商家电话
     */
    private String tel;

}

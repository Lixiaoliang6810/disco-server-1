package com.miner.disco.admin.model.response.merchant;

import java.math.BigDecimal;
import java.util.Date;
import lombok.Getter;
import lombok.Setter;

/**
 * author:linjw Date:2019/1/4 Time:15:36
 */
@Getter
@Setter
public class MerchantDetailResponse {

    /**
     * 主键
     */
    private Long id;
    /**
     * 名称
     */
    private String name;
    /**
     * 地址
     */
    private String address;
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
     * 商家类型
     */
    private Long classifyId;
    /**
     * 商户LOGO
     */
    private String logo;
    /**
     * 营业执照
     */
    private String businessLicense;
    /**
     * 分数
     */
    private Double score;
    /**
     * 排序
     */
    private Integer sort;
    /**
     * 推荐商家(1 - 普通商家  2 - 推荐商家)
     */
    private Integer recommend;
    /**
     * 营业时间
     */
    private String officeHours;
    /**
     * 联系人名称
     */
    private String contactName;
    /**
     * 联系人电话
     */
    private String contactMobile;
    /**
     * 经度
     */
    private Double longitude;
    /**
     * 纬度
     */
    private Double latitude;
    /**
     * 平台抽成比例
     */
    private BigDecimal platformRatio;

    /**
     * 引导员抽成比例
     */
    private BigDecimal vipRatio;

    /**
     * 会员优惠比例
     */
    private BigDecimal memberRatio;
    /**
     * 创建时间
     */
    private Date createDate;
    /**
     * 更新时间
     */
    private Date updateDate;
}

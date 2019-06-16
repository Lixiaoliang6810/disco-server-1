package com.miner.disco.admin.model.response.merchant;

import java.math.BigDecimal;
import java.util.Date;
import lombok.Getter;
import lombok.Setter;

/**
 * author:linjw Date:2019/1/4 Time:15:34
 */
@Getter
@Setter
public class MerchantListResponse {
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
     * 商家类型
     */
    private Long classifyId;
    /**
     * 分数
     */
    private Double score;
    /**
     * 可用余额
     */
    private BigDecimal usableBalance;
    /**
     * 冻结余额
     */
    private BigDecimal frozenBalance;
    /**
     * 推荐商家(1 - 普通商家  2 - 推荐商家)
     */
    private Integer recommend;
    /**
     * 商户状态
     */
    private Integer status;
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
}

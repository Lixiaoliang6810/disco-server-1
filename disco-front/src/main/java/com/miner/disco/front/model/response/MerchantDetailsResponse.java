package com.miner.disco.front.model.response;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author Created by lubycoder@163.com 2018/12/28
 */
@Getter
@Setter
public class MerchantDetailsResponse implements Serializable {

    private static final long serialVersionUID = -6987043184385843094L;
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
     * 分数
     */
    private Double score;
    /**
     * 推荐商家(1 - 普通商家  2 - 推荐商家)
     */
    private Integer recommend;
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
     * 总评论数
     */
    private Integer totalEvaluateCount;
    /**
     * {@link com.miner.disco.basic.constants.BooleanStatus}
     * 是否收藏 (1 - 是  2 - 否)
     */
    private Integer collected;
    /**
     * 服务器时间
     */
    private Long serverTime;

    public Long getServerTime() {
        return System.currentTimeMillis();
    }
}

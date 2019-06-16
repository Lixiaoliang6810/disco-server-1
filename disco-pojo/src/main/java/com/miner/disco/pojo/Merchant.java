package com.miner.disco.pojo;


import com.miner.disco.basic.constants.BasicEnum;
import lombok.*;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
public class Merchant {

    /**
     * 主键
     */
    private Long id;
    /**
     * 手机号(账号)
     */
    private String mobile;
    /**
     * 登陆密码
     */
    private String password;
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
     * 商家类型
     */
    private Long classifyId;
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
    /**
     * 冻结余额
     */
    private BigDecimal frozenBalance;
    /**
     * 营业执照
     */
    private String businessLicense;
    /**
     * 联系人名称
     */
    private String contactName;
    /**
     * 联系人电话
     */
    private String contactMobile;
    /**
     * 定位
     */
    private String geohash;
    /**
     * 当前定位城市
     */
    private String city;
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

    public enum STATUS implements BasicEnum {
        WAIT_SUBMIT(1, "待提交"),
        WAIT_APPROVE(2, "待审核"),
        REJECT(3, "驳回"),
        BUSINESS(4, "营业");

        Integer key;
        String value;

        STATUS(Integer key, String value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public Integer getKey() {
            return this.key;
        }

        @Override
        public String getValue() {
            return this.value;
        }
    }


}

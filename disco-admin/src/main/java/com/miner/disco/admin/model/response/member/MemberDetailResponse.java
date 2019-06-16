package com.miner.disco.admin.model.response.member;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * author:linjw Date:2019/1/3 Time:17:36
 */
@Getter
@Setter
public class MemberDetailResponse implements Serializable {

    private static final long serialVersionUID = -5953388608486245994L;

    /**
     *
     */
    private Long id;
    /**
     * 手机号
     */
    private String mobile;
    /**
     * 昵称
     */
    private String nickname;
    /**
     * 真实姓名
     */
    private String realname;
    /**
     * 领队(1 - 是 2 - 否)
     */
    private Integer leader;
    /**
     * 头像
     */
    private String avatar;
    /**
     * 性别（1 - 男 2 - 女）
     */
    private Integer gender;
    /**
     * 生日
     */
    private Date birthday;
    /**
     * 邀请码
     */
    private String inviteCode;
    /**
     * 标签(多个标签使用逗号拆分)
     */
    private String label;
    /**
     * 标语(签名)
     */
    private String slogan;
    /**
     * 生活照(相册)
     */
    private String liveImages;
    /**
     * 背景图
     */
    private String backgroundImage;
    /**
     * 体重
     */
    private Integer weight;
    /**
     * 身高
     */
    private Integer height;
    /**
     * 城市
     */
    private String city;
    /**
     * 当前城市
     */
    private String currentCity;
    /**
     * 余额
     */
    private BigDecimal balance;
    /**
     * 积分
     */
    private BigDecimal integral;
    /**
     * 经度
     */
    private Double longitude;
    /**
     * 纬度
     */
    private Double latitude;
    /**
     * VIP引导员(1 - 是  2 - 否)
     */
    private Integer vip;
    /**
     * 是否推荐(1 - 是  2 - 否)
     */
    private Integer recommend;
    /**
     * IM账号
     */
    private String imAccount;
    /**
     * 创建时间
     */
    private Date createDate;
    /**
     * 更新时间
     */
    private Date updateDate;
    /**
     * 最后登陆时间
     */
    private Date lastLoginDate;
}

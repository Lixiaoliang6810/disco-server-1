package com.miner.disco.admin.model.response.member;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

/**
 * author:linjw Date:2019/1/3 Time:17:27
 */
@Getter
@Setter
public class MemberListResponse {

    /**
     * 主键
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
     * 邀请码
     */
    private String inviteCode;

    /**
     * 性别（1 - 男 2 - 女）
     */
    private Integer gender;
    /**
     * 余额
     */
    private BigDecimal balance;
    /**
     * 积分
     */
    private BigDecimal integral;
    /**
     * VIP引导员(1 - 否  2 - 是)
     */
    private Integer vip;

    /**
     * 是否推荐(1 - 是  2 - 否)
     */
    private Integer recommend;

    /**
     * 领队(1 - 是 2 - 否)
     */
    private Integer leader;

    /**
     * 最后登陆时间
     */
    private Date lastLoginDate;
    /**
     * 更新
     */
    private Date updateDate;
    /**
     * 创建时间
     */
    private Date createDate;
}

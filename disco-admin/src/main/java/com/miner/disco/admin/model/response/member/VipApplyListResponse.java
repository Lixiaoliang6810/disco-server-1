package com.miner.disco.admin.model.response.member;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * author:linjw Date:2019/1/7 Time:17:04
 */
@Getter
@Setter
public class VipApplyListResponse {

    /**
     * 主键
     */
    private Long id;
    /**
     * 昵称
     */
    private String nickname;

    /**
     * 手机号
     */
    private String mobile;

    /**
     * 真实姓名
     */
    private String fullname;

    /**
     * 出生年月
     */
    private Date birthday;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 身份证号
     */
    private String cardNo;

    /**
     * 创建时间
     */
    private Date createDate;

    /**
     * 更新时间
     */
    private Date updateDate;
}

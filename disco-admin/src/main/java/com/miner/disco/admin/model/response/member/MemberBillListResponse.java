package com.miner.disco.admin.model.response.member;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * author:linjw Date:2019/1/4 Time:11:29
 */
@Getter
@Setter
public class MemberBillListResponse implements Serializable {

    private static final long serialVersionUID = -742893977297566918L;

    /**
     * 主键
     */
    private Long id;
    /**
     * 流水号
     */
    private String serialNo;
    /**
     * 用户昵称
     */
    private String nickname;
    /**
     * 用户手机号
     */
    private String mobile;
    /**
     * 金额
     */
    private BigDecimal amount;
    /**
     * 收支类型（1 - 收入 2 - 支出）
     */
    private Integer type;
    /**
     * 收支类型名称
     */
    private String typeName;
    /**
     * 交易类型
     */
    private Integer tradeType;
    /**
     * 交易类型名称
     */
    private String tradeTypeName;
    /**
     * 来源(对应交易类型)
     */
    private String source;
    /**
     * 备注
     */
    private String remark;
    /**
     * 创建时间
     */
    private Date createDate;

    /**
     * 创建时间
     */
    private Date updateDate;
}

package com.miner.disco.admin.model.response.member;

import java.math.BigDecimal;
import java.util.Date;
import lombok.Getter;
import lombok.Setter;

/**
 * author:linjw Date:2019/1/4 Time:15:12
 */
@Getter
@Setter
public class MemberIntegralListResponse {

    /**
     * 主键
     */
    private Long id;
    /**
     * 流水号
     */
    private String serialNo;
    /**
     * 用户主键
     */
    private Long userId;
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

    public String getTypeName() {
        return typeName;
    }

    public String getTradeTypeName() {
        return tradeTypeName;
    }
}

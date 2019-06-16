package com.miner.disco.pojo;



import lombok.*;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
public class MemberIntegralBill {

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
     * 交易类型
     */
    private Integer tradeType;
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
     * 更新时间
     */
    private Date updateDate;

}

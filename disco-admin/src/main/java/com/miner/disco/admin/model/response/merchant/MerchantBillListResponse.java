package com.miner.disco.admin.model.response.merchant;

import java.math.BigDecimal;
import java.util.Date;
import lombok.Getter;
import lombok.Setter;

/**
 * author:linjw Date:2019/1/14 Time:11:21
 */
@Getter
@Setter
public class MerchantBillListResponse {

    /**
     * 商户主键
     */
    private Long merchantId;
    /**
     * 商户名称
     */
    private String merchantName;
    /**
     * 金额
     */
    private BigDecimal amount;
    /**
     * 收支类型
     */
    private Integer type;
    /**
     * 交易类型
     */
    private Integer tradeType;
    /**
     * 来源
     */
    private Long source;
    /**
     * 可用余额
     */
    private BigDecimal usableBalance;
    /**
     * 冻结余额
     */
    private BigDecimal frozenBalance;
   /**
     * 备注
     */
    private String remark;
    /**
     * 创建时间
     */
    private Date createDate;
}

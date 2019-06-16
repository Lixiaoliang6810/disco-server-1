package com.miner.disco.admin.model.response.merchant;

import java.math.BigDecimal;
import java.util.Date;
import lombok.Getter;
import lombok.Setter;

/**
 * author:linjw Date:2019/2/21 Time:11:38
 */
@Getter
@Setter
public class SettlementApplyListResponse {

    /**
     * 主键
     */
    private Long id;
    /**
     * 商户主键
     */
    private Long merchantId;
    /**
     * 商户名称
     */
    private String merchantName;
    /**
     * 商户电话
     */
    private String mobile;
    /**
     * 结算金额
     */
    private BigDecimal amount;
    /**
     * 状态
     */
    private Integer status;
    /**
     * 审核用户主键
     */
    private Long reviewUserId;
    /**
     * 审核用户名
     */
    private String reviewUsername;
    /**
     * 创建时间
     */
    private Date createDate;
    /**
     * 更新时间
     */
    private Date updateDate;
}

package com.miner.disco.mch.model.response;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author Created by lubycoder@163.com 2019-2-18
 */
@Getter
@Setter
public class MerchantBillListResponse implements Serializable {

    private static final long serialVersionUID = 1592501779562750427L;

    private BigDecimal amount;
    private Integer type;
    private Integer tradeType;
    private String source;
    private Long sourceId;
    private String remark;
    private Date createDate;

}

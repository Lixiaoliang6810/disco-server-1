package com.miner.disco.mch.model.response;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author Created by lubycoder@163.com 2019-2-21
 */
@Setter
@Getter
public class MerchantSettlementRecordResponse implements Serializable {

    private static final long serialVersionUID = 783777002669852672L;

    private Integer status;
    private BigDecimal amount;
    private Date createDate;

}

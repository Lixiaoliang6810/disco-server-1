package com.miner.disco.mch.model.request;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author Created by lubycoder@163.com 2019-2-21
 */
@Getter
@Setter
public class MerchantSettlementApplyRequest implements Serializable {

    private static final long serialVersionUID = -2857320537038868583L;

    private Long merchantId;
    private BigDecimal amount;

}

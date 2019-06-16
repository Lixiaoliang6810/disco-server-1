package com.miner.disco.mch.model.response;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author Created by lubycoder@163.com 2019-2-21
 */
@Getter
@Setter
public class MerchantOfflineBillResponse implements Serializable {

    private static final long serialVersionUID = -1845968169878244134L;

    private BigDecimal amount;
    private Integer payWay;
    private Date createDate;

}

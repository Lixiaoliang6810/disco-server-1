package com.miner.disco.mch.model.request;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author Created by lubycoder@163.com 2019-06-21
 */
@Getter
@Setter
public class ReceivablesQrcodeRequest implements Serializable {

    private static final long serialVersionUID = 5287367118549733578L;

    private Long merchantId;
    private BigDecimal winePrice;
    private BigDecimal foodPrice;
    private String coupon;

}

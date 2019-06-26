package com.miner.disco.mch.model.request;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author Created by lubycoder@163.com 2019/1/8
 */
@Getter
@Setter
public class MerchantGoodsCreateRequest implements Serializable {

    private static final long serialVersionUID = 3587896362555350365L;

    private String name;
    private String images;
    private Long merchantId;
    private BigDecimal price;
    private BigDecimal minimumCharge;

}

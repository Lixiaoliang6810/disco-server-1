package com.miner.disco.mch.model.request;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author Created by lubycoder@163.com 2019/1/9
 */
@Getter
@Setter
public class MerchantInfoModifyRequest implements Serializable {

    private static final long serialVersionUID = -6365158863211421414L;

    private Long id;

    private String slideshow;

    private String logo;

    private String tel;

    private String label;

    private String officeHours;

    private BigDecimal percapita;

}

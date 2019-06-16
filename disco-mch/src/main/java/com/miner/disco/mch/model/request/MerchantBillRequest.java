package com.miner.disco.mch.model.request;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author Created by lubycoder@163.com 2019-2-18
 */
@Getter
@Setter
public class MerchantBillRequest implements Serializable {

    private static final long serialVersionUID = 2283300119739660215L;

    private Long merchantId;
    private Integer year;
    private Integer month;

}

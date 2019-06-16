package com.miner.disco.mch.model.request;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author Created by lubycoder@163.com 2019/1/9
 */
@Getter
@Setter
public class MerchantGoodsBatchOperationRequest implements Serializable {

    private static final long serialVersionUID = -2039857263429553713L;

    private Long merchantId;
    private Long[] goodsIds;

}

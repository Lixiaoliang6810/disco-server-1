package com.miner.disco.admin.model.request.merchant;

import com.miner.disco.basic.model.request.Pagination;
import lombok.Getter;
import lombok.Setter;

/**
 * author:linjw Date:2019/1/14 Time:17:49
 */
@Getter
@Setter
public class MerchantBillSearchRequest extends Pagination {

    /**
     * 商户名称
     */
    private String merchantName;

    /**
     * 来源
     */
    private String source;

    /**
     * 收支类型
     */
    private Integer type;

    /**
     * 交易类型
     */
    private Integer tradeType;
}

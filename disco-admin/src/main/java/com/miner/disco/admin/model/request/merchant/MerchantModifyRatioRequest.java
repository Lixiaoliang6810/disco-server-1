package com.miner.disco.admin.model.request.merchant;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * author:linjw Date:2019/1/18 Time:22:03
 */
@Getter
@Setter
public class MerchantModifyRatioRequest {

    private Long id;

    /**
     * 平台抽成比例
     */
    private BigDecimal platformRatio;

    /**
     * 引导员抽成比例
     */
    private BigDecimal vipRatio;

    /**
     * 会员优惠比例
     */
    private BigDecimal memberRatio;
}

package com.miner.disco.admin.model.request.merchant;

import com.miner.disco.basic.model.request.Pagination;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * author:linjw Date:2019/1/4 Time:17:07
 */
@Getter
@Setter
public class MerchantGoodsSearchRequest extends Pagination implements Serializable {

    private static final long serialVersionUID = -3892206863937452785L;

    /**
     * 商户主键
     */
    private Long merchantId;
    /**
     * 商品名称
     */
    private String name;
    /**
     * 商户名称
     */
    private String merchantName;
    /**
     * 商品状态(1 - 上架 2 - 下架)
     */
    private Integer status;
}

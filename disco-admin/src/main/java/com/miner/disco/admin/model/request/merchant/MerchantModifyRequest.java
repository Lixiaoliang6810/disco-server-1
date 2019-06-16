package com.miner.disco.admin.model.request.merchant;

import lombok.Getter;
import lombok.Setter;

/**
 * author:linjw Date:2019/1/18 Time:22:03
 */
@Getter
@Setter
public class MerchantModifyRequest {

    private Long id;

    /**
     * 排序
     */
    private Integer sort;
    /**
     * 推荐商家(1 - 普通商家  2 - 推荐商家)
     */
    private Integer recommend;
}

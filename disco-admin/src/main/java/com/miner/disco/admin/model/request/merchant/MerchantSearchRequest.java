package com.miner.disco.admin.model.request.merchant;

import com.miner.disco.basic.model.request.Pagination;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * author:linjw Date:2019/1/4 Time:16:19
 */
@Getter
@Setter
public class MerchantSearchRequest extends Pagination implements Serializable {

    private static final long serialVersionUID = -5367667154816673178L;

    /**
     * 名称
     */
    private String name;
    /**
     * 电话
     */
    private String tel;
    /**
     * 商家类型
     */
    private Integer classifyId;
    /**
     * 推荐商家(1 - 普通商家  2 - 推荐商家)
     */
    private Integer recommend;
}

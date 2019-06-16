package com.miner.disco.admin.model.request.member;

import com.miner.disco.basic.model.request.Pagination;
import lombok.Getter;
import lombok.Setter;

/**
 * author:linjw Date:2019/1/8 Time:14:29
 */
@Getter
@Setter
public class CollectSearchRequest extends Pagination {
    /**
     * 用户主键
     */
    private Long userId;
    /**
     * 用户昵称
     */
    private String nickname;
    /**
     * 商户主键
     */
    private Long merchantId;
    /**
     * 商户名称
     */
    private String merchantName;
}

package com.miner.disco.admin.model.request.merchant;

import com.miner.disco.basic.model.request.Pagination;
import lombok.Getter;
import lombok.Setter;

/**
 * author:linjw Date:2019/1/16 Time:15:14
 */
@Getter
@Setter
public class AuditingSearchRequest extends Pagination {

    /**
     * 名称
     */
    private String name;
    /**
     * 地址
     */
    private String address;
    /**
     * 商家类型
     */
    private Long classifyId;
    /**
     * 商户状态
     */
    private Integer status;
    /**
     * 联系人名称
     */
    private String contactName;
    /**
     * 联系人电话
     */
    private String contactMobile;
}

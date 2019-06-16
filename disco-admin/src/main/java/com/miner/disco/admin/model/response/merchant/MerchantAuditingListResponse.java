package com.miner.disco.admin.model.response.merchant;

import java.util.Date;
import lombok.Getter;
import lombok.Setter;

/**
 * author:linjw Date:2019/1/16 Time:15:08
 */
@Getter
@Setter
public class MerchantAuditingListResponse {

    /**
     * 主键
     */
    private Long id;
    /**
     * 手机号(账号)
     */
    private String mobile;
    /**
     * 名称
     */
    private String name;
    /**
     * 地址
     */
    private String address;
    /**
     * 商户LOGO
     */
    private String logo;
    /**
     * 商家类型
     */
    private Long classifyId;
    /**
     * 商户状态
     */
    private Integer status;
    /**
     * 营业执照
     */
    private String businessLicense;
    /**
     * 联系人名称
     */
    private String contactName;
    /**
     * 联系人电话
     */
    private String contactMobile;
    /**
     * 创建时间
     */
    private Date createDate;
    /**
     * 更新时间
     */
    private Date updateDate;
}

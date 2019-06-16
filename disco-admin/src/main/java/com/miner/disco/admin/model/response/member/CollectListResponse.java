package com.miner.disco.admin.model.response.member;

import java.util.Date;
import lombok.Getter;
import lombok.Setter;

/**
 * author:linjw Date:2019/1/8 Time:14:25
 */
@Getter
@Setter
public class CollectListResponse {

    /**
     * 主键
     */
    private Integer id;
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
    /**
     * 创建时间
     */
    private Date createDate;
    /**
     * 更新时间
     */
    private Date updateDate;
}

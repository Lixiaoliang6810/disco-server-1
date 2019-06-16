package com.miner.disco.admin.model.response.system;

import java.util.Date;
import lombok.Getter;
import lombok.Setter;

/**
 * author:linjw Date:2019/1/7 Time:19:53
 */
@Getter
@Setter
public class ConfigListResponse {

    /**
     * 主键
     */
    private Long id;
    /**
     * KEY
     */
    private String key;
    /**
     * VALUE
     */
    private String value;
    /**
     * 备注
     */
    private String remark;
    /**
     * 创建时间
     */
    private Date createDate;
    /**
     * 更新时间
     */
    private Date updateDate;
}

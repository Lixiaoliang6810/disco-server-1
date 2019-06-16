package com.miner.disco.admin.model.request.system;

import lombok.Getter;
import lombok.Setter;

/**
 * author:linjw Date:2019/1/7 Time:19:58
 */
@Getter
@Setter
public class ConfigModifyRequest {

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
}

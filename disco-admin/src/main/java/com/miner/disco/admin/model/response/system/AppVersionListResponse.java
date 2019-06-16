package com.miner.disco.admin.model.response.system;

import java.util.Date;
import lombok.Getter;
import lombok.Setter;

/**
 * author:linjw Date:2019/2/19 Time:11:13
 */
@Getter
@Setter
public class AppVersionListResponse {

    /**
     * 主键
     */
    private Long id;
    /**
     * APP类型 {@link com.miner.disco.basic.constants.AppEnum}
     */
    private String app;
    /**
     * 类型
     */
    private Integer type;
    /**
     * 版本号
     */
    private String version;
    /**
     * 强制更新
     */
    private Integer compulsion;
    /**
     * 创建时间
     */
    private Date createDate;
}

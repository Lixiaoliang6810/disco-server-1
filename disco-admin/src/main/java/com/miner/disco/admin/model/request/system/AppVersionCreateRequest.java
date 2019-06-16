package com.miner.disco.admin.model.request.system;

import lombok.Getter;
import lombok.Setter;

/**
 * author:linjw Date:2019/2/19 Time:11:20
 */
@Getter
@Setter
public class AppVersionCreateRequest {

    /**
     * APP类型 {@link com.miner.disco.basic.constants.AppEnum}
     */
    private String app;
    /**
     * 文件
     */
    private String file;
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
}

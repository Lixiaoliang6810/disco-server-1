package com.miner.disco.admin.model.request.system;

import com.miner.disco.basic.model.request.Pagination;
import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

/**
 * author:linjw Date:2019/2/19 Time:11:21
 */
@Getter
@Setter
public class AppVersionSearchRequest extends Pagination implements Serializable {

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
}

package com.miner.disco.front.model.request;

import com.miner.disco.basic.constants.AppEnum;
import com.miner.disco.pojo.AppVersion;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Created by lubycoder@163.com 2019-2-14
 */
@Getter
@Setter
public class AppVersionCheckRequest {

    private String version;
    private AppEnum app;
    private AppVersion.TYPE type;

}

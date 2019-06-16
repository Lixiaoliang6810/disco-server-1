package com.miner.disco.admin.constant.module.admin;

import com.miner.disco.admin.auth.BasicModule;
import com.miner.disco.admin.constant.GroupEnum;

/**
 * @author: chencx
 * @create: 2019-01-07
 **/
public class AppVersionManager implements BasicModule {
    @Override
    public String getCode() {
        return "system.version";
    }

    @Override
    public String getName() {
        return "app版本管理";
    }

    @Override
    public String group() {
        return GroupEnum.ADMIN.group();
    }
}

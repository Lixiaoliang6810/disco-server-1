package com.miner.disco.admin.constant.module.admin;

import com.miner.disco.admin.auth.BasicModule;
import com.miner.disco.admin.constant.GroupEnum;

/**
 * @author: chencx
 * @create: 2019-01-07
 **/
public class FeedbackManager implements BasicModule {
    @Override
    public String getCode() {
        return "system.feedback";
    }

    @Override
    public String getName() {
        return "意见反馈管理";
    }

    @Override
    public String group() {
        return GroupEnum.ADMIN.group();
    }
}

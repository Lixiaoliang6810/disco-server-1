package com.miner.disco.admin.constant.module.member;

import com.miner.disco.admin.auth.BasicModule;
import com.miner.disco.admin.constant.GroupEnum;

/**
 * @author: chencx
 * @create: 2019-01-07
 **/
public class WithdrawalManager implements BasicModule {
    @Override
    public String getCode() {
        return "member.withdrawal";
    }

    @Override
    public String getName() {
        return "会员提现管理";
    }

    @Override
    public String group() {
        return GroupEnum.ADMIN.group();
    }
}

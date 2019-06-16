package com.miner.disco.admin.constant.module.member;

import com.miner.disco.admin.auth.BasicModule;
import com.miner.disco.admin.constant.GroupEnum;

/**
 * @author: chencx
 * @create: 2019-01-07
 **/
public class MemberManager implements BasicModule {
    @Override
    public String getCode() {
        return "member.user";
    }

    @Override
    public String getName() {
        return "会员用户管理";
    }

    @Override
    public String group() {
        return GroupEnum.ADMIN.group();
    }
}

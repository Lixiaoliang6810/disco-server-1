package com.miner.disco.admin.constant.module.merchant;

import com.miner.disco.admin.auth.BasicModule;
import com.miner.disco.admin.constant.GroupEnum;

/**
 * author:linjw Date:2019/2/21 Time:14:35
 */
public class SettlementApplyManager implements BasicModule {

    @Override
    public String getCode() {
        return "merchant.settlement";
    }

    @Override
    public String getName() {
        return "结算管理";
    }

    @Override
    public String group() {
        return GroupEnum.MERCHANT.group();
    }
}

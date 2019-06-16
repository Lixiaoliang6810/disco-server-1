package com.miner.disco.admin.constant.module.merchant;

import com.miner.disco.admin.auth.BasicModule;
import com.miner.disco.admin.constant.GroupEnum;

/**
 * 商家产品管理
 * @author: chencx
 * @create: 2019-01-07
 **/
public class MerchantProduct implements BasicModule {
    @Override
    public String getCode() {
        return "merchant.product";
    }

    @Override
    public String getName() {
        return "产品管理";
    }

    @Override
    public String group() {
        return GroupEnum.MERCHANT.group();
    }
}

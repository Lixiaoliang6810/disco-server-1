package com.miner.disco.admin.constant.module.merchant;

import com.miner.disco.admin.auth.BasicModule;
import com.miner.disco.admin.constant.GroupEnum;

/**
 * 商家分类管理
 * @author: chencx
 * @create: 2019-01-07
 **/
public class MerchantClassify implements BasicModule {
    @Override
    public String getCode() {
        return "merchant.classify";
    }

    @Override
    public String getName() {
        return "店铺分类";
    }

    @Override
    public String group() {
        return GroupEnum.MERCHANT.group();
    }
}

package com.miner.disco.admin.constant.module.merchant;

import com.miner.disco.admin.auth.BasicModule;
import com.miner.disco.admin.constant.GroupEnum;

/**
 * 商家订单管理
 * @author: chencx
 * @create: 2019-01-07
 **/
public class MerchantOrder implements BasicModule {
    @Override
    public String getCode() {
        return "merchant.order";
    }

    @Override
    public String getName() {
        return "订单管理";
    }

    @Override
    public String group() {
        return GroupEnum.MERCHANT.group();
    }
}

package com.miner.disco.admin.constant.function;

import com.miner.disco.admin.auth.BasicFunction;

/**
 * @author: chencx
 * @create: 2019-01-07
 **/
public class Edit implements BasicFunction {
    @Override
    public String getCode() {
        return "func.update";
    }

    @Override
    public String getName() {
        return "修改";
    }
}

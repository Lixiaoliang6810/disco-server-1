package com.miner.disco.admin.auth;

/**
 * @author: chencx
 * @create: 2018-12-21
 **/
public class DefaultFunc implements BasicFunction {

    @Override
    public String getCode() {
        return "auth.default";
    }

    @Override
    public String getName() {
        return "默认";
    }
}

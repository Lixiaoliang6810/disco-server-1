package com.miner.disco.basic.constants;

/**
 * @author Created by lubycoder@163.com 2019-2-14
 */
public enum AppEnum implements BasicEnum {

    MEMBER(1, "用户端"),

    MERCHANT(2, "商户端");

    Integer key;
    String value;

    @Override
    public Integer getKey() {
        return this.key;
    }

    @Override
    public String getValue() {
        return this.value;
    }

    AppEnum(Integer key, String value) {
        this.key = key;
        this.value = value;
    }
}

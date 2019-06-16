package com.miner.disco.basic.constants;

/**
 * @author Created by lubycoder@163.com 2019/1/8
 */
public enum BooleanStatus implements BasicEnum {

    YES(1, "是"), NO(2, "否");

    Integer key;
    String value;

    BooleanStatus(Integer key, String value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public Integer getKey() {
        return this.key;
    }

    @Override
    public String getValue() {
        return this.value;
    }
}

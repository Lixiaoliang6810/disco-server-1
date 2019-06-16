package com.miner.disco.basic.constants;

/**
 * @author Created by lubycoder@163.com 2018/12/24
 */
public enum DeleteStatus implements BasicEnum {

    NORMAL(1, "正常"),
    DELETE(2, "删除");

    DeleteStatus(Integer key, String value) {
        this.key = key;
        this.value = value;
    }

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
}

package com.miner.disco.basic.constants;

/**
 * @author: chencx
 * @create: 2019-01-08
 **/
public enum StateEnum implements BasicEnum{
    ENABLE(1, "启用"),
    DISABLE(2, "禁用"),
    ;

    private Integer key;

    private String value;

    StateEnum(Integer key, String value){
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

package com.miner.disco.netease.support;

/**
 * Created by lubycoder@163.com on 2017/8/7.
 */
public class NeteaseImResponse {

    /**
     * 操作成功
     */
    public static final int CODE_SUCCESS = 200;

    public static final String DESC_SUCCESS = "already register";

    private int code;

    private String desc;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public boolean isSuccess() {
        return code == CODE_SUCCESS;
    }
}

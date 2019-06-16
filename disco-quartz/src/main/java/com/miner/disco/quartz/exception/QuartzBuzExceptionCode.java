package com.miner.disco.quartz.exception;

/**
 * author:linjw Date:2019/1/14 Time:14:09
 */
public enum QuartzBuzExceptionCode {
    /**
     * 对象不存在
     */
    OBJECT_NOT_EXIST(80000, "对象不存在"),
    /**
     * 订单状态错误
     */
    ORDERS_STATUS_ERROR(80001, "订单状态错误"),

    /**
     * 数据异常,请重试
     */
    DATA_CONVERSION_ERROR(80002, "数据异常,请重试"),

    DATA_VALID_REJECT(80003, "数据验证失败"),
    /**
     * 初始化订单下标错误
     */
    INIT_ORDERS_INDEX_ERROR(80004,"初始化订单下标错误")
    ;
    private int code;

    private String message;

    QuartzBuzExceptionCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}

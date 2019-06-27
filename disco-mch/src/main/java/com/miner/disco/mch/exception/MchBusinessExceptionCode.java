package com.miner.disco.mch.exception;

/**
 * @author Created by lubycoder@163.com 2019/1/8
 */
public enum MchBusinessExceptionCode {

    SMS_CODE_ERROR(10000),

    USERNAME_OR_PASSWORD_ERROR(10001),

    MOBILE_REGISTERED(10002),

    MOBILE_UNREGISTERED(10003),

    NOT_SUFFICIENT_FUNDS(10004),

    NON_LOCAL_ORDERS(20000),

    QRCODE_GENERATE_ERROR(21000),

    ORDERS_STATUS_ERROR(20001),

    OBJECT_CONVERSION_ERROR(90001),

    OBJECT_DOES_NOT_EXIST(90002),

    ILLEGAL_OPERATION(90003);

    int code;

    MchBusinessExceptionCode(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}

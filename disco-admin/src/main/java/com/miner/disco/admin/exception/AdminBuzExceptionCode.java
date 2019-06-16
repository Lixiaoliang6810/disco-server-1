package com.miner.disco.admin.exception;

/**
 * @author: chencx
 * @create: 2018-07-17
 **/
public enum AdminBuzExceptionCode {

    SYSTEM_ERROR(-2, "系统异常"),
    /**
     * 对象不存在
     */
    OBJECT_NOT_EXIST(80000, "对象不存在"),

    /**
     * 非法操作
     */
    ILLEGAL_REQUEST(80001, "非法操作"),

    /**
     * 数据异常,请重试
     */
    DATA_CONVERSION_ERROR(80002, "数据异常,请重试"),

    DATA_VALID_REJECT(80003, "数据验证失败"),

    //业务异常从 10000开始递增

    USER_NAME_ALREADY_EXISTS(10000, "用户名已存在"),

    USER_MOBILE_ALREADY_EXISTS(10001, "手机号已存在"),

    USER_NON_EXISTS(10002, "用户不存在"),

    USER_IS_DISABLE(10003, "用户已被禁用"),

    MERCHANT_STATUS_ERROR(10004,"商家状态错误"),

    WITHDRAWAL_APPLY_ALREADY_REVIEW(10005, "提现申请已审核"),

    WITHDRAWAL_APPLY_HANDLE_ERROR(10006, "提现审核处理转账失败"),

    WITHDRAWAL_APPLY_TRANSFER_ERROR(10007, "提现审核转账失败"),

    SETTLEMENT_APPLY_ALREADY_REVIEW(10008, "结算申请已审核"),

    SETTLEMENT_APPLY_HANDLE_ERROR(10009, "结算审核处理失败"),

    ROLE_NAME_IS_ALREADY(10008, "角色名已存在"),
    ;
    private int code;

    private String message;

    AdminBuzExceptionCode(int code, String message) {
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

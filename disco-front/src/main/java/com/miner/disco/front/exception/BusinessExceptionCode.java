package com.miner.disco.front.exception;

/**
 * @author Created by lubycoder@163.com 2018/12/25
 */
public enum BusinessExceptionCode {

    /**
     * 验证码错误
     */
    SMS_CODE_ERROR(10000),
    /**
     * IN初始化错误
     */
    IM_INITIALIZATION_FAIL(10001),
    /**
     * 用户名或密码错误
     */
    USERNAME_OR_PASSWORD_ERROR(10002),
    /**
     * 用户未注册
     */
    MEMBER_UNREGISTERED(10003),
    /**
     * 用户已注册
     */
    MEMBER_REGISTERED(10004),
    /**
     * 余额不足
     */
    NOT_SUFFICIENT_FUNDS(11000),
    /**
     * 重复支付订单
     */
    ORDERS_REPEATED_PAYMENT(20001),
    /**
     * 订单退款失败
     */
    ORDERS_REFUND_FAILURE(20002),
    /**
     * 订单剩余座位不足
     */
    ORDER_ASSEMBLE_SEATS_INSUFFICIENT(20003),
    /**
     * 订单不支持拼座
     */
    ORDERS_NONSUPPORT_ASSEMBLE(20004),
    /**
     * 不能加入自己得拼座
     */
    CANNOT_JOIN_MYSELF_ASSEMBLE(20005),
    /**
     * 重复申请拼座
     */
    REPEAT_APPLY_ASSEMBLE(20006),
    /**
     * 非法操作
     */
    ILLEGAL_OPERATION(90000),
    /**
     * 参数错误
     */
    PARAMETER_ERROR(90001),
    /**
     * 对象不存在
     */
    OBJECT_NOT_FOUND(90002),
    /**
     * 数据转换错误
     */
    OBJECT_CONVERSION_ERROR(90003);

    int code;

    BusinessExceptionCode(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}

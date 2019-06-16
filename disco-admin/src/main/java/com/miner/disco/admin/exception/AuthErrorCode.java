package com.miner.disco.admin.exception;

/**
 * @author Created by lubycoder@163.com 2018/11/2
 */
public enum AuthErrorCode {

    VERIFICATION_CODE_ERROR(90000, "验证码错误"),

    NO_AUTH(90001, "权限不足"),

    NO_SESSION(90002, "请先登陆"),

    AUTHENTICATION_ERROR(90003, "认证异常"),

    ALREADY_LOGIN(90004, "账号已在另一台设备登陆"),
    ;

    int code;
    String message;

    AuthErrorCode(int code, String message) {
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

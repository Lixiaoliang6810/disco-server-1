package com.miner.disco.front.exception;

import com.miner.disco.basic.exception.BasicException;

/**
 * @author Created by lubycoder@163.com 2018/12/25
 */
public class BusinessException extends BasicException {

    private static final long serialVersionUID = 4494478769704391450L;

    public BusinessException(int code) {
        super(code);
    }

    public BusinessException(Integer code, String message, Object... args) {
        super(code, message, args);
    }

    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(int code, String message) {
        super(code, message);
    }

    public BusinessException(Throwable t) {
        super(t);
    }
}

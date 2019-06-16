package com.miner.disco.basic.exception;

import java.text.MessageFormat;

/**
 * @author Created by lubycoder@163.com 2018/12/21
 */
public class BasicException extends RuntimeException {

    private static final long serialVersionUID = 5681536606697089284L;

    int code;

    public BasicException(int code) {
        this.code = code;
    }

    public BasicException(Integer code, String message, Object... args) {
        super(MessageFormat.format(message, args));
        this.code = code;
    }

    public BasicException(String message) {
        super(message);
    }

    public BasicException(int code, String message) {
        super(message);
        this.code = code;
    }

    public BasicException(Throwable t) {
        super(t);
    }

    public int getCode() {
        return code;
    }

}

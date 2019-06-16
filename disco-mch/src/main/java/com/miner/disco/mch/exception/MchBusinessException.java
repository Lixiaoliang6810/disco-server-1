package com.miner.disco.mch.exception;

import com.miner.disco.basic.exception.BasicException;

/**
 * @author Created by lubycoder@163.com 2019/1/8
 */
public class MchBusinessException extends BasicException {

    private static final long serialVersionUID = 2952469037982293276L;

    public MchBusinessException(int code) {
        super(code);
    }

    public MchBusinessException(Integer code, String message, Object... args) {
        super(code, message, args);
    }

    public MchBusinessException(String message) {
        super(message);
    }

    public MchBusinessException(int code, String message) {
        super(code, message);
    }

    public MchBusinessException(Throwable t) {
        super(t);
    }
}

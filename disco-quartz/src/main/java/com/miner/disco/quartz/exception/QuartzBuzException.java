package com.miner.disco.quartz.exception;

import com.miner.disco.basic.exception.BasicException;

/**
 * author:linjw Date:2019/1/14 Time:14:09
 */
public class QuartzBuzException extends BasicException {

    public QuartzBuzException(int code) {
        super(code);
    }

    public QuartzBuzException(Integer code, String message, Object... args) {
        super(code, message, args);
    }

    public QuartzBuzException(String message) {
        super(message);
    }

    public QuartzBuzException(int code, String message) {
        super(code, message);
    }

    public QuartzBuzException(Throwable t) {
        super(t);
    }
}

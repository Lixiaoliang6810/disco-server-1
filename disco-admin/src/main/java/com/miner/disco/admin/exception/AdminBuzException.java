package com.miner.disco.admin.exception;

import com.miner.disco.basic.exception.BasicException;

/**
 * @author: chencx
 * @create: 2018-07-17 19:50
 **/
public class AdminBuzException extends BasicException {

    public AdminBuzException(Integer code) {
        super(code);
    }

    public AdminBuzException(String message, Integer code) {
        super(code, message);
    }

    public AdminBuzException(Integer code, String message) {
        super(code, message);
    }

    public AdminBuzException(Throwable t) {
        super(t);
    }
}

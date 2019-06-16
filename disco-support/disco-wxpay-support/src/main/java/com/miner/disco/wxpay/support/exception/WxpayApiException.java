package com.miner.disco.wxpay.support.exception;

/**
 * @author Created by lubycoder@163.com 2019/1/10
 */
public class WxpayApiException extends RuntimeException{

    private static final long serialVersionUID = 2869398677364444230L;

    public WxpayApiException(Throwable cause) {
        super(cause);
    }

    public WxpayApiException(String message) {
        super(message);
    }

    public WxpayApiException(String message, Throwable cause) {
        super(message, cause);
    }
}

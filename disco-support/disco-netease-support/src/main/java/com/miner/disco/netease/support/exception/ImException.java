package com.miner.disco.netease.support.exception;

/**
 * @author Created by lubycoder@163.com 2019/1/2
 */
public class ImException extends RuntimeException {

    private static final long serialVersionUID = 878400569281006726L;

    public ImException() {
    }

    public ImException(String message) {
        super(message);
    }

    public ImException(String message, Throwable cause) {
        super(message, cause);
    }
}

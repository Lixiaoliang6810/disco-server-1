package com.zaki.pay.wx.exception;

/**
 * @author: wz1016_vip@163.com @Date: 2019/7/2
 */
public class WXPayApiException extends RuntimeException{
    private static final long serialVersionUID = 8585077313168807574L;

    public WXPayApiException(Throwable cause) {
        super(cause);
    }

    public WXPayApiException(String message) {
        super(message);
    }

    public WXPayApiException(String message, Throwable cause) {
        super(message, cause);
    }
}

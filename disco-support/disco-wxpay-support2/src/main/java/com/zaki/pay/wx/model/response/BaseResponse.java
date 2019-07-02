package com.zaki.pay.wx.model.response;

import lombok.Getter;
import lombok.Setter;

/**
 * @author: wz1016_vip@163.com @Date: 2019/7/2
 */
@Getter
@Setter
public class BaseResponse {
    /**
     * 返回状态码
     */
    private String returnCode;
    /**
     * 返回信息
     */
    private String returnMsg;
}

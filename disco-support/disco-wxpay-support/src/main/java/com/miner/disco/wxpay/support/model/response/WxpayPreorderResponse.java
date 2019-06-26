package com.miner.disco.wxpay.support.model.response;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * Created by lubycoder@163.com on 2018/4/5.
 */
@Getter
@Setter
public class WxpayPreorderResponse implements Serializable {


    private String packageStr = "Sign=WXPay";
    private String appId;
    private String timeStamp;
    private String nonceStr;
    private String paySign;
    private String partnerid;
    private String prepayid;
    private String codeUrl;
    private String returnCode;

}

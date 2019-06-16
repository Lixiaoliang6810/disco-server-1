package com.miner.disco.alipay.support.model.request;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * Created by lubycoder@163.com on 2018/4/5.
 */
@Getter
@Setter
public class AlipayPreorderRequest implements Serializable {

    private static final long serialVersionUID = -8496120144690816752L;

    private String body;
    private String subject;
    private String outTradeNo;
    private String timeoutExpress = "2h";
    private String totalAmount;
    private String productCode = "QUICK_MSECURITY_PAY";
    private String callbackParam;
    private String callbackUrl;
}

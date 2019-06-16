package com.miner.disco.alipay.support.model.request;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author Created by lubycoder@163.com 2019/1/9
 */
@Getter
@Setter
public class AlipayRefundRequest implements Serializable {

    private static final long serialVersionUID = -2645012476569101428L;

    private String outTradeNo;
    private String tradeNo;
    private String refundAmount;
    private String refundReason;

}

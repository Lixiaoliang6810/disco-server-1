package com.miner.disco.mch.model.request;

import com.miner.disco.basic.constants.Payment;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Map;

/**
 * @author Created by lubycoder@163.com 2019-06-21
 */
@Getter
@Setter
public class SweepPaymentNotifyRequest implements Serializable {

    private static final long serialVersionUID = 7645779315882462978L;

    private String outTradeNo;
    private Payment payment;
    private String notifyId;
    private BigDecimal amount;
    private Map<String, String> metadata;

}

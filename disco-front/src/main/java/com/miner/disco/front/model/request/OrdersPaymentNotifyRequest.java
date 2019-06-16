package com.miner.disco.front.model.request;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Map;

/**
 * @author Created by lubycoder@163.com 2019/1/10
 */
@Getter
@Setter
public class OrdersPaymentNotifyRequest implements Serializable {

    private static final long serialVersionUID = -8660892406895045109L;

    private Long ordersId;
    private BigDecimal amount;
    private String notifyId;
    private OrdersPaymentRequest.PAYMENT_METHOD paymentMethod;
    private Map<String, String> metadata;

}

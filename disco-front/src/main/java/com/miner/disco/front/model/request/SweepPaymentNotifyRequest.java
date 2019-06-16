package com.miner.disco.front.model.request;

import com.miner.disco.basic.constants.Payment;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Map;

/**
 * @author Created by lubycoder@163.com 2019-2-21
 */
@Getter
@Setter
public class SweepPaymentNotifyRequest implements Serializable {

    private static final long serialVersionUID = -3653927336290546367L;

    private Long vipId;
    private Long qrcodeId;
    private Long memberId;
    private Payment payment;
    private String notifyId;
    private BigDecimal amount;
    private Map<String, String> metadata;

}

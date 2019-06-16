package com.miner.disco.front.model.request;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author Created by lubycoder@163.com 2019/1/9
 */
@Getter
@Setter
public class WithdrawalApplyRequest implements Serializable {

    private static final long serialVersionUID = 1033052089274867070L;

    private Long userId;
    private Long bankId;
    private BigDecimal amount;

}

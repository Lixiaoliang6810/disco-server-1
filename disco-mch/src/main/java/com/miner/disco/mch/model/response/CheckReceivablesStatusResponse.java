package com.miner.disco.mch.model.response;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@Setter
public class CheckReceivablesStatusResponse implements Serializable {

    private static final long serialVersionUID = -3123123549404155469L;

    private Integer status;
    private BigDecimal amount;

}
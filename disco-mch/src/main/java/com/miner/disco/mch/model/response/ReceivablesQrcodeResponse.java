package com.miner.disco.mch.model.response;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author Created by lubycoder@163.com 2019/06/11
 */
@Getter
@Setter
public class ReceivablesQrcodeResponse implements Serializable {

    private static final long serialVersionUID = 7452617459642350522L;

    private String qrcode;
    private String aliQrcode;
    private String wxQrcode;
    private String outTradeNo;
    private BigDecimal originalPrice;
    private BigDecimal discountPrice;

}

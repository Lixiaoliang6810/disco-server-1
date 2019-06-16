package com.miner.disco.alipay.support.model.request;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author Created by lubycoder@163.com 2019/1/17
 */
@Getter
@Setter
public class AlipayTransferRequest implements Serializable {
    private static final long serialVersionUID = 2073435298032761285L;

    private String outBizNo;
    private PAYEE_TYPE payeeType;
    private BigDecimal amount;
    private String payeeAccount;
    private String payeeRealname;

    public enum PAYEE_TYPE {
        ALIPAY_USERID,
        ALIPAY_LOGONID
    }

}

package com.miner.disco.front.model.response;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author Created by lubycoder@163.com 2019/06/12
 */
@Getter
@Setter
public class VipBillListResponse implements Serializable {

    private static final long serialVersionUID = 5778902295140490041L;

    private String serialNo;
    private BigDecimal amount;
    private Integer type;
    private Integer tradeType;
    private String source;
    private String remark;
    private Date createDate;

}

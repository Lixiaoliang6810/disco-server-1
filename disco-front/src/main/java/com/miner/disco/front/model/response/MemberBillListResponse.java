package com.miner.disco.front.model.response;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author Created by lubycoder@163.com 2019/1/15
 */
@Getter
@Setter
public class MemberBillListResponse implements Serializable {

    private static final long serialVersionUID = -971330328957571618L;

    private String serialNo;
    private BigDecimal amount;
    private Integer type;
    private Integer tradeType;
    private String source;
    private String remark;
    private Date createDate;

}

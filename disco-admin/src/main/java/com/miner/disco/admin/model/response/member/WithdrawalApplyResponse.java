package com.miner.disco.admin.model.response.member;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author Created by lubycoder@163.com 2019/1/9
 */
@Getter
@Setter
public class WithdrawalApplyResponse implements Serializable {

    private static final long serialVersionUID = -1723822064545157438L;

    private Long id;

    private Long userId;

    private String nickname;

    private String mobile;

    private String cardNo;

    private String bankName;

    private Integer type;

    private BigDecimal amount;

    private Integer status;

    private String matadata;

    private Date createDate;

    private Date updateDate;

}

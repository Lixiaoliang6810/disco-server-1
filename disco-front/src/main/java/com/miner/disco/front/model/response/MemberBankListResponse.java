package com.miner.disco.front.model.response;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author Created by lubycoder@163.com 2019/1/14
 */
@Getter
@Setter
public class MemberBankListResponse implements Serializable {

    private static final long serialVersionUID = 290439396255038183L;

    /**
     * 主键
     */
    private Long id;
    /**
     * 卡类型
     */
    private Integer type;
    /**
     * 银行卡号
     */
    private String cardNo;
    /**
     * 银行名称
     */
    private String bankName;
    /**
     * 持卡人
     */
    private String cardholder;

}

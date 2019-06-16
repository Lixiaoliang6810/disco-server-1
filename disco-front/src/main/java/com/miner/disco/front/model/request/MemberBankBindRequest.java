package com.miner.disco.front.model.request;

import com.miner.disco.pojo.MemberBank;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author Created by lubycoder@163.com 2019/1/14
 */
@Getter
@Setter
public class MemberBankBindRequest implements Serializable {

    private static final long serialVersionUID = 7906944487080125586L;

    private Long userId;
    private MemberBank.TYPE type;
    private String cardNo;
    private String bankName;
    private String cardholder;
    private String mobile;

}

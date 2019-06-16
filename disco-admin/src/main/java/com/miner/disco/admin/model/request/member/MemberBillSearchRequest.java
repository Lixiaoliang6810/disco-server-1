package com.miner.disco.admin.model.request.member;

import com.miner.disco.basic.model.request.Pagination;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * author:linjw Date:2019/1/4 Time:11:40
 */
@Getter
@Setter
public class MemberBillSearchRequest extends Pagination implements Serializable {

    private static final long serialVersionUID = 3351185197264450562L;

    /**
     * 手机号
     */
    private String mobile;
    /**
     * 昵称
     */
    private String nickname;
    /**
     * 真实姓名
     */
    private String realname;
    /**
     * 收支类型（1 - 收入 2 - 支出）
     */
    private Integer type;
    /**
     * 交易类型
     */
    private Integer tradeType;
}

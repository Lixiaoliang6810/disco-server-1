package com.miner.disco.admin.model.request.member;

import com.miner.disco.basic.model.request.Pagination;
import lombok.Getter;
import lombok.Setter;

/**
 * author:linjw Date:2019/1/4 Time:15:23
 */
@Getter
@Setter
public class MemberIntegralSearchRequest extends Pagination {

    private static final long serialVersionUID = -6387586329417562790L;

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

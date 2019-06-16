package com.miner.disco.admin.model.request.member;

import com.miner.disco.basic.model.request.Pagination;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * author:linjw Date:2019/1/3 Time:17:30
 */
@Getter
@Setter
public class MemberSearchRequest extends Pagination implements Serializable {

    private static final long serialVersionUID = -876023724529928742L;
    
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
     * VIP
     */
    private Integer vip;
}

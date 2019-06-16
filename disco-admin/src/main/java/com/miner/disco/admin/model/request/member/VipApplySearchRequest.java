package com.miner.disco.admin.model.request.member;

import com.miner.disco.basic.model.request.Pagination;
import lombok.Getter;
import lombok.Setter;

/**
 * author:linjw Date:2019/1/7 Time:17:14
 */
@Getter
@Setter
public class VipApplySearchRequest extends Pagination {

    /**
     * 状态
     */
    private Integer status;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 真实姓名
     */
    private String fullname;
}

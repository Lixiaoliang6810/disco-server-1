package com.miner.disco.admin.model.request.merchant;

import lombok.Getter;
import lombok.Setter;

/**
 * author:linjw Date:2019/2/21 Time:14:23
 */
@Getter
@Setter
public class SettlementApplyReviewRequest {

    /**
     * 申请主键
     */
    private Long id;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 审核用户主键
     */
    private Long reviewUserId;
}

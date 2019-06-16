package com.miner.disco.admin.model.request.orders;

import com.miner.disco.basic.model.request.Pagination;
import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

/**
 * author:linjw Date:2019/1/7 Time:16:28
 */
@Getter
@Setter
public class InvitationSearchRequest extends Pagination implements Serializable {

    private static final long serialVersionUID = -5098267860525554268L;

    /**
     * 被邀请人名称
     */
    private String inviteeName;

    /**
     * 订单号
     */
    private String no;
}

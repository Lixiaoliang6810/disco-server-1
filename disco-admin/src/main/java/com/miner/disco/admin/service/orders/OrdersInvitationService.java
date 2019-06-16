package com.miner.disco.admin.service.orders;

import com.miner.disco.admin.model.request.orders.InvitationSearchRequest;
import com.miner.disco.basic.model.response.PageResponse;

/**
 * author:linjw Date:2019/1/7 Time:16:35
 */
public interface OrdersInvitationService {

    /**
     * 订单邀约列表
     * @param search
     * @return
     */
    PageResponse InvitationList(InvitationSearchRequest search);
}

package com.miner.disco.admin.dao.orders;

import com.miner.disco.admin.model.request.orders.InvitationSearchRequest;
import com.miner.disco.admin.model.response.orders.InvitationListResponse;
import com.miner.disco.basic.model.request.Pagination;
import com.miner.disco.mybatis.support.BasicMapper;
import com.miner.disco.pojo.OrdersInvitation;

import java.util.List;

/**
 * author:linjw Date:2019/1/7 Time:16:10
 */
public interface OrdersInvitationDao extends BasicMapper<OrdersInvitation> {

    List<InvitationListResponse> invitationList(InvitationSearchRequest search);

    int countList(InvitationSearchRequest search);

}

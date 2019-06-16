package com.miner.disco.admin.service.orders.impl;

import com.miner.disco.admin.dao.orders.OrdersInvitationDao;
import com.miner.disco.admin.model.request.orders.InvitationSearchRequest;
import com.miner.disco.admin.model.response.orders.InvitationListResponse;
import com.miner.disco.admin.service.orders.OrdersInvitationService;
import com.miner.disco.basic.model.response.PageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * author:linjw Date:2019/1/7 Time:16:35
 */
@Service
public class OrdersInvitationServiceImpl implements OrdersInvitationService {

    @Autowired
    private OrdersInvitationDao ordersInvitationDao;

    @Override
    public PageResponse InvitationList(InvitationSearchRequest search) {
        List<InvitationListResponse> listResponses = ordersInvitationDao.invitationList(search);
        int total = ordersInvitationDao.countList(search);
        return new PageResponse(total, listResponses);
    }
}

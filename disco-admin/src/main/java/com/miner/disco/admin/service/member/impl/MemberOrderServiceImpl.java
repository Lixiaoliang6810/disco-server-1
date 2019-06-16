package com.miner.disco.admin.service.member.impl;

import com.miner.disco.admin.dao.orders.OrdersDao;
import com.miner.disco.admin.exception.AdminBuzException;
import com.miner.disco.admin.model.request.member.MemberOrderPageRequest;
import com.miner.disco.admin.model.response.member.MemberOrderPageResponse;
import com.miner.disco.admin.service.member.MemberOrderService;
import com.miner.disco.basic.model.response.PageResponse;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: chencx
 * @create: 2019-01-17
 **/
@Service
public class MemberOrderServiceImpl implements MemberOrderService {

    @Autowired
    private OrdersDao ordersDao;

    @Override
    public PageResponse findPage(MemberOrderPageRequest page) throws AdminBuzException {
        List<MemberOrderPageResponse> dataList = ordersDao.queryMemberOrderPage(page);
        Integer count = 0;
        if (CollectionUtils.isNotEmpty(dataList)){
            count = ordersDao.queryMemberOrderPageCount(page);
        }
        return PageResponse.builder().list(dataList).total(count).build();
    }
}

package com.miner.disco.admin.service.orders.impl;

import com.miner.disco.admin.dao.orders.OrdersComplainDao;
import com.miner.disco.admin.exception.AdminBuzException;
import com.miner.disco.admin.model.response.orders.ComplainListResponse;
import com.miner.disco.admin.service.orders.OrdersComplainService;
import com.miner.disco.basic.model.request.Pagination;
import com.miner.disco.basic.model.response.PageResponse;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * author:linjw Date:2019/1/7 Time:15:43
 */
@Service
public class OrdersComplainServiceImpl implements OrdersComplainService {

    @Autowired
    private OrdersComplainDao complainDao;

    @Override
    public PageResponse complainList(Pagination page, String no) throws AdminBuzException {
        List<ComplainListResponse> listResponses = complainDao.complainList(page, no);
        int total = complainDao.countList(no);
        return new PageResponse(total, listResponses);
    }
}

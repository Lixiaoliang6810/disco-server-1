package com.miner.disco.admin.service.member.impl;

import com.miner.disco.admin.dao.member.MemberIntegralBillDao;
import com.miner.disco.admin.exception.AdminBuzException;
import com.miner.disco.admin.model.request.member.MemberIntegralSearchRequest;
import com.miner.disco.admin.model.response.member.MemberIntegralListResponse;
import com.miner.disco.admin.service.member.MemberIntegralBillService;
import com.miner.disco.basic.model.request.Pagination;
import com.miner.disco.basic.model.response.PageResponse;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * author:linjw Date:2019/1/4 Time:11:57
 */
@Service
public class MemberIntegralBillServiceImpl implements MemberIntegralBillService {

    @Autowired
    private MemberIntegralBillDao memberIntegralBillDao;

    @Override
    public PageResponse queryIntegralBillList(MemberIntegralSearchRequest search) throws AdminBuzException {
        List<MemberIntegralListResponse> listResponses = memberIntegralBillDao.integralBillList(search);
        int total = memberIntegralBillDao.countIntegralBill(search);
        return new PageResponse(total, listResponses);
    }
}

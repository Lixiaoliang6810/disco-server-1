package com.miner.disco.admin.service.member.impl;

import com.miner.disco.admin.dao.member.MemberBillDao;
import com.miner.disco.admin.model.request.member.MemberBillSearchRequest;
import com.miner.disco.admin.model.response.member.MemberBillListResponse;
import com.miner.disco.admin.service.member.MemberBillService;
import com.miner.disco.basic.model.response.PageResponse;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * author:linjw Date:2019/1/4 Time:11:27
 */
@Service
public class MemberBillServiceImpl implements MemberBillService {

    @Autowired
    private MemberBillDao memberBillDao;

    @Override
    public PageResponse memberBillList(MemberBillSearchRequest request) {
        List<MemberBillListResponse> listResponses = memberBillDao.memberBillList(request);
        int total = 0;
        if (CollectionUtils.isNotEmpty(listResponses)) {
            total = memberBillDao.countMemberBill(request);
        }
        return PageResponse.builder().list(listResponses).total(total).build();
    }
}

package com.miner.disco.front.service.impl;

import com.miner.disco.front.dao.MemberBillMapper;
import com.miner.disco.front.exception.BusinessException;
import com.miner.disco.front.model.request.MemberBillListRequest;
import com.miner.disco.front.model.response.MemberBillListResponse;
import com.miner.disco.front.service.MemberBillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Created by lubycoder@163.com 2019/1/15
 */
@Service
public class MemberBillServiceImpl implements MemberBillService {

    @Autowired
    private MemberBillMapper memberBillMapper;

    @Override
    @Transactional(readOnly = true)
    public List<MemberBillListResponse> list(MemberBillListRequest request) throws BusinessException {
        return memberBillMapper.queryByUserId(request);
    }
}

package com.miner.disco.front.service.impl;

import com.miner.disco.front.dao.InviteRecordMapper;
import com.miner.disco.front.exception.BusinessException;
import com.miner.disco.front.model.request.InviteRecordListRequest;
import com.miner.disco.front.model.response.InviteRecordListResponse;
import com.miner.disco.front.service.InviteRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Created by lubycoder@163.com 2019/1/16
 */
@Service
public class InviteRecordServiceImpl implements InviteRecordService {

    @Autowired
    private InviteRecordMapper inviteRecordMapper;

    @Override
    @Transactional(readOnly = true)
    public List<InviteRecordListResponse> list(InviteRecordListRequest request) throws BusinessException {
        return inviteRecordMapper.queryByUserId(request);
    }
}

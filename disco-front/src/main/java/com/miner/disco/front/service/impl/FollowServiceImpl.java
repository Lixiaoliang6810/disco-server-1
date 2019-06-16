package com.miner.disco.front.service.impl;

import com.miner.disco.front.dao.FollowRecordMapper;
import com.miner.disco.front.exception.BusinessException;
import com.miner.disco.front.model.request.FollowListRequest;
import com.miner.disco.front.model.response.FollowListResponse;
import com.miner.disco.pojo.FollowRecord;
import com.miner.disco.front.service.FollowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @author Created by lubycoder@163.com 2018/12/24
 */
@Service
public class FollowServiceImpl implements FollowService {

    @Autowired
    private FollowRecordMapper followRecordMapper;

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void follow(Long idolId, Long fansId) throws BusinessException {
        FollowRecord followRecord = new FollowRecord();
        followRecord.setIdolId(idolId);
        followRecord.setFansId(fansId);
        followRecord.setCreateDate(new Date());
        followRecord.setUpdateDate(new Date());
        followRecordMapper.insert(followRecord);
    }

    @Override
    public void unfollow(Long idolId, Long fansId) throws BusinessException {

    }

    @Override
    public List<FollowListResponse> list(FollowListRequest request) throws BusinessException {
        return null;
    }
}

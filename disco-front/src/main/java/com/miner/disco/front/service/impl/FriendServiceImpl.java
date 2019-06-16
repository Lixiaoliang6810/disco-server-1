package com.miner.disco.front.service.impl;

import com.miner.disco.basic.constants.DeleteStatus;
import com.miner.disco.front.dao.FriendMapper;
import com.miner.disco.front.exception.BusinessException;
import com.miner.disco.front.model.request.FriendListRequest;
import com.miner.disco.front.model.response.FriendListResponse;
import com.miner.disco.front.service.FriendService;
import com.miner.disco.pojo.Friend;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @author Created by lubycoder@163.com 2019/1/3
 */
@Service
public class FriendServiceImpl implements FriendService {

    @Autowired
    private FriendMapper friendMapper;

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void add(Long wonUserId, Long himUserId) throws BusinessException {
        Friend friend = new Friend();
        friend.setOwnUserId(wonUserId);
        friend.setHimUserId(himUserId);
        friend.setDeleted(DeleteStatus.NORMAL.getKey());
        friend.setCreateDate(new Date());
        friend.setUpdateDate(new Date());
        friendMapper.insert(friend);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void remove(Long wonUserId, Long himUserId) throws BusinessException {
        Friend friend = friendMapper.queryByOwnAndHim(wonUserId, himUserId);
        if (friend == null) return;
        friend.setDeleted(DeleteStatus.DELETE.getKey());
        friendMapper.updateByPrimaryKey(friend);
    }

    @Override
    public List<FriendListResponse> list(FriendListRequest request) throws BusinessException {
        return friendMapper.queryByUserId(request);
    }
}

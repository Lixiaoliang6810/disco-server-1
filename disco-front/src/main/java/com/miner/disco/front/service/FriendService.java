package com.miner.disco.front.service;

import com.miner.disco.front.exception.BusinessException;
import com.miner.disco.front.model.request.FriendListRequest;
import com.miner.disco.front.model.response.FriendListResponse;

import java.util.List;

/**
 * @author Created by lubycoder@163.com 2019/1/3
 */
public interface FriendService {


    void add(Long wonUserId, Long himUserId) throws BusinessException;

    void remove(Long wonUserId, Long himUserId) throws BusinessException;

    List<FriendListResponse> list(FriendListRequest request) throws BusinessException;

}

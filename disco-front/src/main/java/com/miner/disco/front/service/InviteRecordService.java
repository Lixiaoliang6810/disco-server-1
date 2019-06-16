package com.miner.disco.front.service;

import com.miner.disco.front.exception.BusinessException;
import com.miner.disco.front.model.request.InviteRecordListRequest;
import com.miner.disco.front.model.response.InviteRecordListResponse;

import java.util.List;

/**
 * @author Created by lubycoder@163.com 2019/1/16
 */
public interface InviteRecordService {

    List<InviteRecordListResponse> list(InviteRecordListRequest request) throws BusinessException;

}

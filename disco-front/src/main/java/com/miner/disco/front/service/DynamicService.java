package com.miner.disco.front.service;

import com.miner.disco.front.exception.BusinessException;
import com.miner.disco.front.model.request.DynamicsListRequest;
import com.miner.disco.front.model.request.MemberPhotosRequest;
import com.miner.disco.front.model.request.ReportRequest;
import com.miner.disco.front.model.response.DynamicsListResponse;
import com.miner.disco.front.model.response.VipMemberListResponse;

import java.util.List;

/**
 * @author Created by lubycoder@163.com 2018/12/24
 */
public interface DynamicService {

    void publish(Long userId, String content, String images) throws BusinessException;

    List<DynamicsListResponse> list(DynamicsListRequest request) throws BusinessException;

    List<String> photos(MemberPhotosRequest request) throws BusinessException;

//    void del(Long id);


    void del(Long id);

    List<DynamicsListResponse> screenList(Long currentUserId, List<DynamicsListResponse> dynamics);
}

package com.miner.disco.front.service;

import com.miner.disco.front.exception.BusinessException;
import com.miner.disco.front.model.request.FollowListRequest;
import com.miner.disco.front.model.response.FollowListResponse;

import java.util.List;

/**
 * @author Created by lubycoder@163.com 2018/12/24
 */
public interface FollowService {

    /**
     * 关注玩家
     *
     * @param idolId 偶像主键
     * @param fansId 粉丝主键
     * @throws BusinessException
     */
    void follow(Long idolId, Long fansId) throws BusinessException;

    /**
     * 取消关注
     *
     * @param idolId 偶像主键
     * @param fansId 粉丝主键
     * @throws BusinessException
     */
    void unfollow(Long idolId, Long fansId) throws BusinessException;

    /**
     * 关注列表
     *
     * @param request 用户主键
     * @return
     * @throws BusinessException
     */
    List<FollowListResponse> list(FollowListRequest request) throws BusinessException;

}

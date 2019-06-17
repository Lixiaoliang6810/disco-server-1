package com.miner.disco.front.service;

import com.miner.disco.front.exception.BusinessException;
import com.miner.disco.front.model.request.MemberInfoModifyRequest;
import com.miner.disco.front.model.request.MemberRegisterRequest;
import com.miner.disco.front.model.request.VipMemberListRequest;
import com.miner.disco.front.model.response.MemberMeCenterResponse;
import com.miner.disco.front.model.response.MemberTaCenterResponse;
import com.miner.disco.front.model.response.VipMemberListResponse;
import com.miner.disco.pojo.Member;
import com.miner.disco.pojo.Shield;

import java.util.List;

/**
 * @author Created by lubycoder@163.com 2018/12/24
 */
public interface MemberService {

    Member findUserByUsername(String username) throws BusinessException;

    boolean exist(String mobile) throws BusinessException;

    void register(MemberRegisterRequest request) throws BusinessException;

    void resetLoginPassword(String mobile, String digit, String password) throws BusinessException;

    void modifyInfo(MemberInfoModifyRequest request) throws BusinessException;

    MemberMeCenterResponse meCenter(Long userId) throws BusinessException;

    MemberTaCenterResponse taCenter(Long currentUserId, Long userId) throws BusinessException;

    List<VipMemberListResponse> vips(VipMemberListRequest request) throws BusinessException;

    Long chatSession(String imAccount) throws BusinessException;

}

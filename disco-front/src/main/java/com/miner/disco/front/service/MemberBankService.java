package com.miner.disco.front.service;

import com.miner.disco.front.exception.BusinessException;
import com.miner.disco.front.model.request.MemberBankBindRequest;
import com.miner.disco.front.model.request.MemberBankListRequest;
import com.miner.disco.front.model.response.MemberBankListResponse;

import java.util.List;

/**
 * @author Created by lubycoder@163.com 2019/1/14
 */
public interface MemberBankService {

    void bind(MemberBankBindRequest request) throws BusinessException;

    void unbind(Long userId, Long bankId) throws BusinessException;

    List<MemberBankListResponse> list(MemberBankListRequest request) throws BusinessException;

}

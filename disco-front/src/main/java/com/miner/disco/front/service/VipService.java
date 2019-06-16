package com.miner.disco.front.service;

import com.miner.disco.front.exception.BusinessException;
import com.miner.disco.front.model.request.MemberVipApplyRequest;
import com.miner.disco.front.model.request.VipBillListRequest;
import com.miner.disco.front.model.response.MemberVipApplyInfoResponse;
import com.miner.disco.front.model.response.VipBillListResponse;

import java.util.List;

/**
 * @author Created by lubycoder@163.com 2019/1/8
 */
public interface VipService {

    void apply(MemberVipApplyRequest request) throws BusinessException;

    MemberVipApplyInfoResponse info(Long userId) throws BusinessException;

    List<VipBillListResponse> bill(VipBillListRequest request) throws BusinessException;

}

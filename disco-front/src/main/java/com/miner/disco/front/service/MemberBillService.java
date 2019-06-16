package com.miner.disco.front.service;

import com.miner.disco.front.exception.BusinessException;
import com.miner.disco.front.model.request.MemberBillListRequest;
import com.miner.disco.front.model.response.MemberBillListResponse;

import java.util.List;

/**
 * @author Created by lubycoder@163.com 2019/1/15
 */
public interface MemberBillService {

    List<MemberBillListResponse> list(MemberBillListRequest request) throws BusinessException;

}

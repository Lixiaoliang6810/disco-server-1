package com.miner.disco.front.dao;

import com.miner.disco.front.model.request.MemberBankListRequest;
import com.miner.disco.front.model.response.MemberBankListResponse;
import com.miner.disco.mybatis.support.BasicMapper;
import com.miner.disco.pojo.MemberBank;

import java.util.List;

/**
 * @author Created by lubycoder@163.com 2019/1/14
 */
public interface MemberBankMapper extends BasicMapper<MemberBank> {

    List<MemberBankListResponse> queryByUserId(MemberBankListRequest request);

}

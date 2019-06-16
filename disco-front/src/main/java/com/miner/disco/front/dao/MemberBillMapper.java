package com.miner.disco.front.dao;

import com.miner.disco.front.model.request.MemberBillListRequest;
import com.miner.disco.front.model.request.VipBillListRequest;
import com.miner.disco.front.model.response.MemberBillListResponse;
import com.miner.disco.front.model.response.VipBillListResponse;
import com.miner.disco.pojo.MemberBill;
import com.miner.disco.mybatis.support.BasicMapper;

import java.util.List;

/**
 * @author Created by lubycoder@163.com 2018/12/24
 */
public interface MemberBillMapper extends BasicMapper<MemberBill> {

    List<MemberBillListResponse> queryByUserId(MemberBillListRequest request);

    List<VipBillListResponse> queryByVipUserId(VipBillListRequest request);

}

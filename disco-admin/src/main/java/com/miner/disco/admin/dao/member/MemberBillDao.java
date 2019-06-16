package com.miner.disco.admin.dao.member;

import com.miner.disco.admin.model.request.member.MemberBillSearchRequest;
import com.miner.disco.admin.model.response.member.MemberBillListResponse;
import com.miner.disco.mybatis.support.BasicMapper;
import com.miner.disco.pojo.MemberBill;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * author:linjw Date:2019/1/4 Time:11:28
 */
public interface MemberBillDao extends BasicMapper<MemberBill> {

    List<MemberBillListResponse> memberBillList(@Param("search") MemberBillSearchRequest search);

    int countMemberBill(@Param("search") MemberBillSearchRequest search);
}

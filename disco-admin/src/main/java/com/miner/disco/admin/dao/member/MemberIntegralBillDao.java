package com.miner.disco.admin.dao.member;

import com.miner.disco.admin.model.request.member.MemberIntegralSearchRequest;
import com.miner.disco.admin.model.response.member.MemberIntegralListResponse;
import com.miner.disco.basic.model.request.Pagination;
import java.util.List;

/**
 * author:linjw Date:2019/1/4 Time:11:55
 */
public interface MemberIntegralBillDao {

    List<MemberIntegralListResponse> integralBillList(MemberIntegralSearchRequest search);

    int countIntegralBill(MemberIntegralSearchRequest search);
}

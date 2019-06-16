package com.miner.disco.admin.service.member;

import com.miner.disco.admin.model.request.member.MemberBillSearchRequest;
import com.miner.disco.basic.model.request.Pagination;
import com.miner.disco.basic.model.response.PageResponse;

/**
 * author:linjw Date:2019/1/4 Time:11:26
 */
public interface MemberBillService {

    /**
     * 查询用户帐单
     * @param request
     * @return
     */
    PageResponse memberBillList(MemberBillSearchRequest request);
}

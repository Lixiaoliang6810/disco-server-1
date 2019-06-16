package com.miner.disco.admin.service.member;

import com.miner.disco.admin.exception.AdminBuzException;
import com.miner.disco.admin.model.request.member.MemberIntegralSearchRequest;
import com.miner.disco.basic.model.request.Pagination;
import com.miner.disco.basic.model.response.PageResponse;

/**
 * author:linjw Date:2019/1/4 Time:11:56
 */
public interface MemberIntegralBillService {

    /**
     * 积分账单列表
     */
    PageResponse queryIntegralBillList(MemberIntegralSearchRequest search) throws AdminBuzException;

}

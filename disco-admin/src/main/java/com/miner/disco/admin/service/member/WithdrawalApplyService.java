package com.miner.disco.admin.service.member;

import com.miner.disco.admin.exception.AdminBuzException;
import com.miner.disco.admin.model.request.member.WithdrawalApplyRequest;
import com.miner.disco.basic.model.response.PageResponse;

/**
 * @author: chencx
 * @create: 2019-01-17
 **/
public interface WithdrawalApplyService {

    /**
     * 分页查询提现申请
     * @param search
     * @return
     * @throws AdminBuzException
     */
    PageResponse findPage(WithdrawalApplyRequest search) throws AdminBuzException;

    /**
     * 审核
     * @throws AdminBuzException
     */
    String review(Long reviewUserId, Long id, Integer status) throws AdminBuzException;
}

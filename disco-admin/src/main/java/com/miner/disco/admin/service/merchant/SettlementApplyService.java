package com.miner.disco.admin.service.merchant;

import com.miner.disco.admin.exception.AdminBuzException;
import com.miner.disco.admin.model.request.merchant.SettlementApplyReviewRequest;
import com.miner.disco.admin.model.request.merchant.SettlementApplySearchRequest;
import com.miner.disco.basic.model.response.PageResponse;

/**
 * author:linjw Date:2019/2/21 Time:11:57
 */
public interface SettlementApplyService {

    PageResponse list(SettlementApplySearchRequest searchRequest) throws AdminBuzException;

    void auditing(Long reviewUserId,SettlementApplyReviewRequest reviewRequest) throws AdminBuzException;
}

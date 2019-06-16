package com.miner.disco.admin.service.member;

import com.miner.disco.admin.exception.AdminBuzException;
import com.miner.disco.admin.model.request.member.VipApplyAuditingRequest;
import com.miner.disco.admin.model.request.member.VipApplySearchRequest;
import com.miner.disco.admin.model.response.member.VipApplyDetailsResponse;
import com.miner.disco.basic.model.response.PageResponse;

/**
 * author:linjw Date:2019/1/7 Time:16:50
 */
public interface VipApplyService {

    /**
     * 查询vip申请列表
     * @param searchRequest
     * @return
     * @throws AdminBuzException
     */
    PageResponse list(VipApplySearchRequest searchRequest) throws AdminBuzException;

    /**
     * VIP申请详情
     * @return
     * @throws AdminBuzException
     */
    VipApplyDetailsResponse details(Long id) throws AdminBuzException;

    /**
     * 审核vip用户申请
     * @param SysUserId 系统用户主键
     * @param auditingRequest
     * @throws AdminBuzException
     */
    void review(Long SysUserId, VipApplyAuditingRequest auditingRequest) throws AdminBuzException;
}

package com.miner.disco.admin.service.member.impl;

import com.miner.disco.admin.dao.member.MemberDao;
import com.miner.disco.admin.dao.member.VipApplyDao;
import com.miner.disco.admin.exception.AdminBuzException;
import com.miner.disco.admin.exception.AdminBuzExceptionCode;
import com.miner.disco.admin.model.request.member.VipApplyAuditingRequest;
import com.miner.disco.admin.model.request.member.VipApplySearchRequest;
import com.miner.disco.admin.model.response.member.VipApplyDetailsResponse;
import com.miner.disco.admin.model.response.member.VipApplyListResponse;
import com.miner.disco.admin.service.member.VipApplyService;
import com.miner.disco.basic.assertion.Assert;
import com.miner.disco.basic.constants.BooleanStatus;
import com.miner.disco.basic.model.response.PageResponse;
import com.miner.disco.pojo.Member;
import com.miner.disco.pojo.VipApply;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * author:linjw Date:2019/1/7 Time:16:50
 */
@Service
public class VipApplyServiceImpl implements VipApplyService {

    @Autowired
    private VipApplyDao vipApplyDao;

    @Autowired
    private MemberDao memberDao;

    @Override
    @Transactional(readOnly = true)
    public PageResponse list(VipApplySearchRequest searchRequest) throws AdminBuzException {
        List<VipApplyListResponse> listResponses = vipApplyDao.list(searchRequest);
        int total = 0;
        if (CollectionUtils.isNotEmpty(listResponses)){
            total = vipApplyDao.countList(searchRequest);
        }
        return PageResponse.builder().list(listResponses).total(total).build();
    }

    @Override
    @Transactional(readOnly = true)
    public VipApplyDetailsResponse details(Long id) throws AdminBuzException {
        VipApplyDetailsResponse details = vipApplyDao.details(id);
        return details;
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void review(Long SysUserId, VipApplyAuditingRequest auditingRequest) throws AdminBuzException {
        if (auditingRequest.getStatus() != VipApply.STATUS.AUDIT_ADOPT.getKey() && auditingRequest.getStatus() != VipApply.STATUS.AUDIT_REJECT.getKey()){
            throw new AdminBuzException(AdminBuzExceptionCode.ILLEGAL_REQUEST.getCode(), AdminBuzExceptionCode.ILLEGAL_REQUEST.getMessage());
        }
        VipApply vipApply = vipApplyDao.queryByPrimaryKey(auditingRequest.getId());
        Assert.notNull(vipApply, AdminBuzExceptionCode.OBJECT_NOT_EXIST.getCode(), "申请单不存在");

        vipApply = vipApplyDao.queryByPrimaryKeyForUpdate(auditingRequest.getId());

        if (auditingRequest.getStatus() == VipApply.STATUS.AUDIT_REJECT.getKey()){
            vipApply.setAuditOpinion(auditingRequest.getAuditOpinion());
        }
        if (auditingRequest.getStatus() == VipApply.STATUS.AUDIT_ADOPT.getKey()){
            vipApply.setAuditOpinion("审核通过");
            Member member = new Member();
            member.setId(vipApply.getUserId());
            member.setVip(BooleanStatus.YES.getKey());
            memberDao.updateByPrimaryKey(member);
        }
        vipApply.setStatus(auditingRequest.getStatus());
        vipApply.setReviewUserId(SysUserId);
        vipApply.setUpdateDate(new Date());
        vipApplyDao.updateByPrimaryKey(vipApply);

    }
}

package com.miner.disco.admin.service.member.impl;

import com.miner.disco.admin.dao.member.MemberDao;
import com.miner.disco.admin.exception.AdminBuzException;
import com.miner.disco.admin.exception.AdminBuzExceptionCode;
import com.miner.disco.admin.model.request.member.MemberSearchRequest;
import com.miner.disco.admin.model.response.member.MemberDetailResponse;
import com.miner.disco.admin.model.response.member.MemberListResponse;
import com.miner.disco.admin.service.member.MemberService;
import com.miner.disco.basic.assertion.Assert;
import com.miner.disco.basic.constants.BooleanStatus;
import com.miner.disco.basic.model.response.PageResponse;
import com.miner.disco.basic.util.DtoTransition;
import com.miner.disco.pojo.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * author:linjw Date:2019/1/3 Time:16:53
 */
@Service
public class MemberServiceImpl implements MemberService {

    @Autowired
    private MemberDao memberDao;

    @Override
    @Transactional(readOnly = true)
    public PageResponse queryMemberList(MemberSearchRequest searchRequest) throws AdminBuzException {
        List<MemberListResponse> listResponses = memberDao.memberList(searchRequest);
        int total = memberDao.countMember(searchRequest);
        return new PageResponse(total, listResponses);
    }

    @Override
    @Transactional(readOnly = true)
    public MemberDetailResponse details(Long id) throws AdminBuzException {
        Member member = memberDao.queryByPrimaryKey(id);
        Assert.notNull(member, AdminBuzExceptionCode.OBJECT_NOT_EXIST.getCode(), "用户不存在");
        return (MemberDetailResponse) DtoTransition.trans(MemberDetailResponse.class, member);
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = RuntimeException.class)
    public void updateRecommend(Long id, Integer recommend) throws AdminBuzException {
        Member member = memberDao.queryByPrimaryKey(id);
        Assert.notNull(member, AdminBuzExceptionCode.OBJECT_NOT_EXIST.getCode(), "会员不存在");
        if (member.getRecommend() != BooleanStatus.YES.getKey() && member.getRecommend() != BooleanStatus.NO.getKey()){
            throw new AdminBuzException(AdminBuzExceptionCode.ILLEGAL_REQUEST.getCode(), AdminBuzExceptionCode.ILLEGAL_REQUEST.getMessage());
        }
        Member update = new Member();
        update.setId(id);
        update.setRecommend(recommend);
        memberDao.updateByPrimaryKey(update);
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = RuntimeException.class)
    public void updateLeader(Long id) throws AdminBuzException {
        Member member = memberDao.queryByPrimaryKey(id);
        Assert.notNull(member, AdminBuzExceptionCode.OBJECT_NOT_EXIST.getCode(), "会员不存在");
       /* if (member.getLeader() != BooleanStatus.YES.getKey() && member.getLeader() != BooleanStatus.NO.getKey()){
            throw new AdminBuzException(AdminBuzExceptionCode.ILLEGAL_REQUEST.getCode(), AdminBuzExceptionCode.ILLEGAL_REQUEST.getMessage());
        }*/
        Member update = new Member();
        update.setId(id);
        update.setVip(BooleanStatus.YES.getKey());
        update.setLeader(BooleanStatus.YES.getKey());
        memberDao.updateByPrimaryKey(update);
    }
}

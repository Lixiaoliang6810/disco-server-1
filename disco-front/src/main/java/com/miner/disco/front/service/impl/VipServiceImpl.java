package com.miner.disco.front.service.impl;

import com.miner.disco.basic.assertion.Assert;
import com.miner.disco.basic.constants.BooleanStatus;
import com.miner.disco.basic.util.DtoTransition;
import com.miner.disco.front.dao.MemberBillMapper;
import com.miner.disco.front.dao.MemberMapper;
import com.miner.disco.front.dao.VipApplyMapper;
import com.miner.disco.front.exception.BusinessException;
import com.miner.disco.front.exception.BusinessExceptionCode;
import com.miner.disco.front.model.request.MemberBillListRequest;
import com.miner.disco.front.model.request.MemberVipApplyRequest;
import com.miner.disco.front.model.request.VipBillListRequest;
import com.miner.disco.front.model.response.MemberVipApplyInfoResponse;
import com.miner.disco.front.model.response.VipBillListResponse;
import com.miner.disco.front.service.VipService;
import com.miner.disco.pojo.Member;
import com.miner.disco.pojo.VipApply;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @author Created by lubycoder@163.com 2019/1/8
 */
@Service
public class VipServiceImpl implements VipService {

    @Autowired
    private VipApplyMapper vipApplyMapper;

    @Autowired
    private MemberMapper memberMapper;

    @Autowired
    private MemberBillMapper memberBillMapper;

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void apply(MemberVipApplyRequest request) throws BusinessException {
        // TODO: 2019/06/11  需求变动，VIP申请只需填写姓名，且申请后直接通过无需审核
        VipApply vipApply = (VipApply) DtoTransition.trans(VipApply.class, request);
        Assert.notNull(vipApply, BusinessExceptionCode.OBJECT_CONVERSION_ERROR.getCode(), "数据转换错误");
        vipApply.setStatus(VipApply.STATUS.AUDIT_ADOPT.getKey());
        vipApply.setCreateDate(new Date());
        vipApply.setUpdateDate(new Date());
        vipApplyMapper.insert(vipApply);

        Member member = new Member();
        member.setId(request.getUserId());
        member.setVip(BooleanStatus.YES.getKey());
        member.setUpdateDate(new Date());
        memberMapper.updateByPrimaryKey(member);
    }

    @Override
    public MemberVipApplyInfoResponse info(Long userId) throws BusinessException {
        VipApply vipApply = vipApplyMapper.queryLastApplyByUserId(userId);
        if (vipApply == null) return null;
        return (MemberVipApplyInfoResponse) DtoTransition.trans(MemberVipApplyInfoResponse.class, vipApply);
    }

    @Override
    public List<VipBillListResponse> bill(VipBillListRequest request) throws BusinessException {
        return memberBillMapper.queryByVipUserId(request);
    }

}

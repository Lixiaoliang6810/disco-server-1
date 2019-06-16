package com.miner.disco.front.service.impl;

import com.miner.disco.basic.assertion.Assert;
import com.miner.disco.basic.constants.DeleteStatus;
import com.miner.disco.basic.util.DtoTransition;
import com.miner.disco.front.dao.MemberBankMapper;
import com.miner.disco.front.exception.BusinessException;
import com.miner.disco.front.exception.BusinessExceptionCode;
import com.miner.disco.front.model.request.MemberBankBindRequest;
import com.miner.disco.front.model.request.MemberBankListRequest;
import com.miner.disco.front.model.response.MemberBankListResponse;
import com.miner.disco.front.service.MemberBankService;
import com.miner.disco.pojo.MemberBank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @author Created by lubycoder@163.com 2019/1/14
 */
@Service
public class MemberBankServiceImpl implements MemberBankService {

    @Autowired
    private MemberBankMapper memberBankMapper;

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void bind(MemberBankBindRequest request) throws BusinessException {
        MemberBank bank = (MemberBank) DtoTransition.trans(MemberBank.class, request);
        Assert.notNull(bank, BusinessExceptionCode.OBJECT_CONVERSION_ERROR.getCode(), "数据转换错误");
        bank.setCreateDate(new Date());
        bank.setUpdateDate(new Date());
        bank.setDeleted(DeleteStatus.NORMAL.getKey());
        bank.setType(request.getType().getKey());
        bank.setBankName(MemberBank.TYPE.ALIPAY == request.getType() ? MemberBank.TYPE.ALIPAY.name() : request.getBankName());
        memberBankMapper.insert(bank);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void unbind(Long userId, Long bankId) throws BusinessException {
        MemberBank memberBank = memberBankMapper.queryByPrimaryKey(bankId);
        if (memberBank.getUserId().longValue() != userId) {
            throw new BusinessException(BusinessExceptionCode.ILLEGAL_OPERATION.getCode(), "非法操作");
        }
        MemberBank saveMemberBank = new MemberBank();
        saveMemberBank.setId(memberBank.getId());
        saveMemberBank.setDeleted(DeleteStatus.DELETE.getKey());
        memberBankMapper.updateByPrimaryKey(saveMemberBank);
    }

    @Override
    public List<MemberBankListResponse> list(MemberBankListRequest request) throws BusinessException {
        return memberBankMapper.queryByUserId(request);
    }

}

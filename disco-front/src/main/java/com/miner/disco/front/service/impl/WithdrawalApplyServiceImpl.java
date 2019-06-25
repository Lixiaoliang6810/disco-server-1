package com.miner.disco.front.service.impl;

import com.miner.disco.basic.assertion.Assert;
import com.miner.disco.basic.util.UidMaskUtils;
import com.miner.disco.front.dao.MemberBillMapper;
import com.miner.disco.front.dao.MemberMapper;
import com.miner.disco.front.dao.WithdrawalApplyMapper;
import com.miner.disco.front.exception.BusinessException;
import com.miner.disco.front.exception.BusinessExceptionCode;
import com.miner.disco.front.model.request.WithdrawalApplyRequest;
import com.miner.disco.front.service.WithdrawalApplyService;
import com.miner.disco.pojo.Member;
import com.miner.disco.pojo.MemberBill;
import com.miner.disco.pojo.WithdrawalApply;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * @author Created by lubycoder@163.com 2019/1/9
 */
@Service
public class WithdrawalApplyServiceImpl implements WithdrawalApplyService {

    @Autowired
    private MemberMapper memberMapper;

    @Autowired
    private WithdrawalApplyMapper withdrawalApplyMapper;

    @Autowired
    private MemberBillMapper memberBillMapper;

    @Override
    @Transactional(rollbackFor = RuntimeException.class, isolation = Isolation.READ_COMMITTED)
    public void apply(WithdrawalApplyRequest request) throws BusinessException {
        Member member = memberMapper.queryByPrimaryKey(request.getUserId());
        Assert.notNull(member, BusinessExceptionCode.ILLEGAL_OPERATION.getCode(), "非法操作");
        if (member.getBalance().compareTo(request.getAmount()) < 0) {
            throw new BusinessException(BusinessExceptionCode.NOT_SUFFICIENT_FUNDS.getCode(), "余额不足");
        }
        member = memberMapper.queryByPrimaryKeyForUpdate(request.getUserId());

//        Member saveMember = new Member();
//        saveMember.setId(member.getId());
//        saveMember.setBalance(member.getBalance().subtract(request.getAmount()));
//        memberMapper.updateByPrimaryKey(saveMember);

        WithdrawalApply withdrawalApply = new WithdrawalApply();
        withdrawalApply.setUserId(member.getId());
        withdrawalApply.setBankId(request.getBankId());
        withdrawalApply.setAmount(request.getAmount());
        withdrawalApply.setCreateDate(new Date());
        withdrawalApply.setUpdateDate(new Date());
        withdrawalApply.setStatus(WithdrawalApply.STATUS.WAIT_PROCESS.getKey());
        withdrawalApplyMapper.insert(withdrawalApply);

        String sno = String.format("%s%s%s",
                DateFormatUtils.format(new Date(), "yyMMdd"),
                UidMaskUtils.idToCode(member.getId()),
                System.currentTimeMillis());
        MemberBill memberBill = new MemberBill();
        memberBill.setSerialNo(sno);
        memberBill.setType(MemberBill.TYPE.OUT.getKey());
        memberBill.setSourceId(withdrawalApply.getId());
        memberBill.setAmount(request.getAmount());
        memberBill.setTradeType(MemberBill.TRADE_TYPE.WITHDRAW_DEPOSIT.getKey());
        memberBill.setSource(MemberBill.TRADE_TYPE.WITHDRAW_DEPOSIT.getValue());
        memberBill.setRemark(MemberBill.TRADE_TYPE.WITHDRAW_DEPOSIT.getValue());
        memberBill.setCreateDate(new Date());
        memberBill.setUpdateDate(new Date());
        memberBillMapper.insert(memberBill);

    }
}

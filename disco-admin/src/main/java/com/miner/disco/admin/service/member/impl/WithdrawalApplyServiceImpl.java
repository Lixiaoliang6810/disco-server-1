package com.miner.disco.admin.service.member.impl;

import com.alipay.api.AlipayApiException;
import com.alipay.api.response.AlipayFundTransToaccountTransferResponse;
import com.miner.disco.admin.dao.member.MemberBankDao;
import com.miner.disco.admin.dao.member.MemberDao;
import com.miner.disco.admin.dao.member.WithdrawalApplyDao;
import com.miner.disco.admin.exception.AdminBuzException;
import com.miner.disco.admin.exception.AdminBuzExceptionCode;
import com.miner.disco.admin.exception.AliPayErrorCode;
import com.miner.disco.admin.model.request.member.WithdrawalApplyRequest;
import com.miner.disco.admin.model.response.member.WithdrawalApplyResponse;
import com.miner.disco.admin.service.member.WithdrawalApplyService;
import com.miner.disco.alipay.support.AlipayService;
import com.miner.disco.alipay.support.model.request.AlipayTransferRequest;
import com.miner.disco.basic.assertion.Assert;
import com.miner.disco.basic.model.response.PageResponse;
import com.miner.disco.basic.util.JsonParser;
import com.miner.disco.pojo.Member;
import com.miner.disco.pojo.MemberBank;
import com.miner.disco.pojo.WithdrawalApply;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author: chencx
 * @create: 2019-01-17
 **/
@Slf4j
@Service
public class WithdrawalApplyServiceImpl implements WithdrawalApplyService {

    @Autowired
    private WithdrawalApplyDao withdrawalApplyDao;

    @Autowired
    private MemberBankDao memberBankDao;

    @Autowired
    private MemberDao memberDao;
    @Override
    public PageResponse findPage(WithdrawalApplyRequest search) throws AdminBuzException {
        List<WithdrawalApplyResponse> dates = withdrawalApplyDao.queryPage(search);
        Integer count = 0;
        if (CollectionUtils.isNotEmpty(dates)){
            count = withdrawalApplyDao.queryPageCount(search);
        }
        return PageResponse.builder().list(dates).total(count).build();
    }


    @Autowired
    private AlipayService alipayService;

    @Override
    @Transactional(readOnly = false, rollbackFor = RuntimeException.class)
    public String review(Long reviewUserId, Long id, Integer status) throws AdminBuzException {
        if (status != WithdrawalApply.STATUS.ADOPT_APPLY.getKey() && status != WithdrawalApply.STATUS.REJECT_APPLY.getKey()){
            throw new AdminBuzException(AdminBuzExceptionCode.ILLEGAL_REQUEST.getCode(), AdminBuzExceptionCode.ILLEGAL_REQUEST.getMessage());
        }
        String message = "审核成功";
        WithdrawalApply withdrawalApply = withdrawalApplyDao.queryByPrimaryKey(id);
        Assert.notNull(withdrawalApply, AdminBuzExceptionCode.OBJECT_NOT_EXIST.getCode(), "数据错误");

        withdrawalApply = withdrawalApplyDao.queryByPrimaryKeyForUpdate(id);

        if (withdrawalApply.getStatus() != WithdrawalApply.STATUS.WAIT_PROCESS.getKey()) {
            throw new AdminBuzException(AdminBuzExceptionCode.WITHDRAWAL_APPLY_ALREADY_REVIEW.getCode(), AdminBuzExceptionCode.WITHDRAWAL_APPLY_ALREADY_REVIEW.getMessage());
        }

        Assert.notNull(withdrawalApply.getBankId(), AdminBuzExceptionCode.OBJECT_NOT_EXIST.getCode(), "没有账户");
        MemberBank memberBank = memberBankDao.queryByPrimaryKey(withdrawalApply.getBankId());
        Assert.notNull(memberBank, AdminBuzExceptionCode.OBJECT_NOT_EXIST.getCode(), "账户不存在");

        WithdrawalApply update = new WithdrawalApply();
        update.setId(id);
        update.setStatus(status);
        update.setReviewUserId(reviewUserId);

        // 审核通过后才更改余额
        if (status == WithdrawalApply.STATUS.ADOPT_APPLY.getKey()){
            log.info("================ start ali pay applyId = {}, reviewUserId={} ======================", id, reviewUserId);
            AlipayTransferRequest alipayTransferRequest = new AlipayTransferRequest();
            alipayTransferRequest.setOutBizNo(String.valueOf(System.currentTimeMillis()));
            alipayTransferRequest.setAmount(withdrawalApply.getAmount());
            alipayTransferRequest.setPayeeType(AlipayTransferRequest.PAYEE_TYPE.ALIPAY_LOGONID);
            alipayTransferRequest.setPayeeAccount(memberBank.getCardNo());
            alipayTransferRequest.setPayeeRealname(memberBank.getCardholder());
            log.info("alipay transfer Amount={}，PayeeAccount={}，PayeeRealname={}", withdrawalApply.getAmount(), memberBank.getCardNo(), memberBank.getBankName());
            try {
                AlipayFundTransToaccountTransferResponse response = alipayService.transfer(alipayTransferRequest);
                log.info(" alipayTransferRequest sueecss = {} ", response.isSuccess());
                String matadata = JsonParser.serializeToJson(response.getBody());
                log.info(" response matadata = {} ", matadata);
                update.setMatadata(matadata);

                // 更改余额
                Member member = memberDao.queryByPrimaryKeyForUpdate(withdrawalApply.getUserId());
                Member updateMember = new Member();
                BeanUtils.copyProperties(member,updateMember);
                updateMember.setBalance(updateMember.getBalance().subtract(withdrawalApply.getAmount()));
                memberDao.updateByPrimaryKey(updateMember);
                if (!response.isSuccess()){
                    String subCode = response.getSubCode();
                    message = AliPayErrorCode.ERROR_CODE.get(subCode);
                    log.info(" transfer fail message {} ", message);
                    update.setStatus(withdrawalApply.getStatus());
                    if (StringUtils.isBlank(message)) {
                        throw new AdminBuzException(AdminBuzExceptionCode.SYSTEM_ERROR.getCode(), AdminBuzExceptionCode.SYSTEM_ERROR.getMessage());
                    }
                }
            } catch (AlipayApiException e) {
                log.info(" AlipayApiException error {}", e);
                throw new AdminBuzException(AdminBuzExceptionCode.WITHDRAWAL_APPLY_HANDLE_ERROR.getCode(), "转账失败");
            }
            log.info("================ end ali pay ======================");
        }
        withdrawalApplyDao.updateByPrimaryKey(update);
        return message;
    }
}

package com.miner.disco.admin.service.merchant.impl;

import com.miner.disco.admin.dao.merchant.MerchantDao;
import com.miner.disco.admin.exception.AdminBuzException;
import com.miner.disco.admin.exception.AdminBuzExceptionCode;
import com.miner.disco.admin.model.request.merchant.*;
import com.miner.disco.admin.model.response.merchant.MerchantAuditingListResponse;
import com.miner.disco.admin.model.response.merchant.MerchantDetailResponse;
import com.miner.disco.admin.model.response.merchant.MerchantListResponse;
import com.miner.disco.admin.service.merchant.MerchantService;
import com.miner.disco.basic.assertion.Assert;
import com.miner.disco.basic.model.response.PageResponse;
import com.miner.disco.basic.util.DtoTransition;
import com.miner.disco.pojo.Merchant;
import com.miner.disco.pojo.Merchant.STATUS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * author:linjw Date:2019/1/4 Time:16:30
 */
@Service
public class MerchantServiceImpl implements MerchantService {

    @Autowired
    private MerchantDao merchantDao;

    @Override
    public PageResponse list(MerchantSearchRequest searchRequest) throws AdminBuzException {
        List<MerchantListResponse> listResponses = merchantDao.merchantList(searchRequest);
        int total = merchantDao.countMerchantList(searchRequest);
        return new PageResponse(total, listResponses);
    }

    @Override
    public MerchantDetailResponse detail(Long id) throws AdminBuzException {
        Merchant merchant = merchantDao.queryByPrimaryKey(id);
        MerchantDetailResponse response = (MerchantDetailResponse) DtoTransition.trans(MerchantDetailResponse.class, merchant);
        Assert.notNull(response, AdminBuzExceptionCode.DATA_CONVERSION_ERROR.getCode());
        return response;
    }

    @Override
    public PageResponse auditingList(AuditingSearchRequest searchRequest) throws AdminBuzException {
        List<MerchantAuditingListResponse> listResponses = merchantDao.auditingList(searchRequest);
        Integer total = merchantDao.countAuditingList(searchRequest);
        return new PageResponse(total, listResponses);
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = RuntimeException.class)
    public void auditing(AuditingRequest auditingRequest) throws AdminBuzException {
        Merchant merchant = merchantDao.queryByPrimaryKey(auditingRequest.getId());
        Assert.notNull(merchant, AdminBuzExceptionCode.OBJECT_NOT_EXIST.getCode());
        Assert.isTrue(merchant.getStatus().intValue() == STATUS.WAIT_APPROVE.getKey(), AdminBuzExceptionCode.MERCHANT_STATUS_ERROR.getCode());
        Merchant saveMerchant = new Merchant();
        saveMerchant.setId(auditingRequest.getId());
        saveMerchant.setStatus(auditingRequest.getStatus());
        if (auditingRequest.getStatus().intValue() == STATUS.BUSINESS.getKey()){
            saveMerchant.setPlatformRatio(auditingRequest.getPlatformRatio());
            saveMerchant.setVipRatio(auditingRequest.getVipRatio());
            saveMerchant.setMemberRatio(auditingRequest.getMemberRatio());
        }
        merchantDao.updateByPrimaryKey(saveMerchant);
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = RuntimeException.class)
    public void modify(MerchantModifyRequest modifyRequest) throws AdminBuzException {
        Merchant merchant = merchantDao.queryByPrimaryKey(modifyRequest.getId());
        Assert.notNull(merchant, AdminBuzExceptionCode.OBJECT_NOT_EXIST.getCode());
        merchant.setSort(modifyRequest.getSort());
        merchant.setRecommend(modifyRequest.getRecommend());
        merchantDao.updateByPrimaryKey(merchant);
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = RuntimeException.class)
    public void modifyRatio(MerchantModifyRatioRequest merchantModifyRatioRequest) throws AdminBuzException {
        Merchant merchant = merchantDao.queryByPrimaryKey(merchantModifyRatioRequest.getId());
        Assert.notNull(merchant, AdminBuzExceptionCode.OBJECT_NOT_EXIST.getCode());
        Merchant update = (Merchant) DtoTransition.trans(Merchant.class, merchantModifyRatioRequest);
        merchantDao.updateByPrimaryKey(update);
    }


}

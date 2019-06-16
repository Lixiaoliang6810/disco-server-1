package com.miner.disco.admin.service.merchant;

import com.miner.disco.admin.exception.AdminBuzException;
import com.miner.disco.admin.model.request.merchant.*;
import com.miner.disco.admin.model.response.merchant.MerchantDetailResponse;
import com.miner.disco.basic.model.request.Pagination;
import com.miner.disco.basic.model.response.PageResponse;
import com.miner.disco.mybatis.support.BasicMapper;
import com.miner.disco.pojo.Merchant;

/**
 * author:linjw Date:2019/1/4 Time:16:28
 */
public interface MerchantService {

    /**
     * 商户列表
     * @param searchRequest
     * @return
     * @throws AdminBuzException
     */
    PageResponse list(MerchantSearchRequest searchRequest) throws AdminBuzException;

    /**
     * 商户详情
     * @param id
     * @return
     * @throws AdminBuzException
     */
    MerchantDetailResponse detail(Long id) throws AdminBuzException;

    /**
     * 商家审核列表
     * @param searchRequest
     * @return
     * @throws AdminBuzException
     */
    PageResponse auditingList(AuditingSearchRequest searchRequest) throws AdminBuzException;

    /**
     * 商家设讷河
     * @param auditingRequest
     * @throws AdminBuzException
     */
    void auditing(AuditingRequest auditingRequest) throws AdminBuzException;

    /**
     * 修改
     * @param modifyRequest
     * @throws AdminBuzException
     */
    void modify(MerchantModifyRequest modifyRequest) throws AdminBuzException;

    /**
     * 比列修改
     * @param merchantModifyRatioRequest
     * @throws AdminBuzException
     */
    void modifyRatio(MerchantModifyRatioRequest merchantModifyRatioRequest) throws AdminBuzException;
}

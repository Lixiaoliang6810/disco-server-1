package com.miner.disco.admin.dao.merchant;

import com.miner.disco.admin.model.request.merchant.AuditingSearchRequest;
import com.miner.disco.admin.model.request.merchant.MerchantSearchRequest;
import com.miner.disco.admin.model.response.merchant.MerchantAuditingListResponse;
import com.miner.disco.admin.model.response.merchant.MerchantListResponse;
import com.miner.disco.mybatis.support.BasicMapper;
import com.miner.disco.pojo.Merchant;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * author:linjw Date:2019/1/4 Time:15:32
 */
public interface MerchantDao extends BasicMapper<Merchant> {

    List<MerchantListResponse> merchantList(@Param(value = "search") MerchantSearchRequest search);

    int countMerchantList(@Param(value = "search")MerchantSearchRequest search);

    List<MerchantAuditingListResponse> auditingList(@Param(value = "search") AuditingSearchRequest search);

    int countAuditingList(@Param(value = "search") AuditingSearchRequest search);

    Merchant queryForUpdate(@Param(value = "id") Long id);
}

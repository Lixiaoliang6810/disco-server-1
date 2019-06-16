package com.miner.disco.admin.dao.merchant;

import com.miner.disco.admin.model.request.merchant.SettlementApplySearchRequest;
import com.miner.disco.admin.model.response.merchant.SettlementApplyListResponse;
import com.miner.disco.mybatis.support.BasicMapper;
import com.miner.disco.pojo.MerchantSettlementApply;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * author:linjw Date:2019/2/21 Time:11:36
 */
public interface SettlementApplyDao extends BasicMapper<MerchantSettlementApply> {

    List<SettlementApplyListResponse> list(@Param(value = "search") SettlementApplySearchRequest search);

    int countList(@Param(value = "search") SettlementApplySearchRequest search);

}

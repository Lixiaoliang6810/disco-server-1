package com.miner.disco.admin.dao.merchant;

import com.miner.disco.admin.model.request.merchant.MerchantBillSearchRequest;
import com.miner.disco.admin.model.response.merchant.MerchantBillListResponse;
import com.miner.disco.mybatis.support.BasicMapper;
import com.miner.disco.pojo.MerchantBill;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * author:linjw Date:2019/1/14 Time:11:20
 */
public interface MerchantBillDao extends BasicMapper<MerchantBill> {

    List<MerchantBillListResponse> billList(@Param(value = "search") MerchantBillSearchRequest search);

    int countBillList(@Param(value = "search") MerchantBillSearchRequest search);
}

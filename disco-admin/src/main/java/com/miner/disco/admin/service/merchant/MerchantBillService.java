package com.miner.disco.admin.service.merchant;

import com.miner.disco.admin.exception.AdminBuzException;
import com.miner.disco.admin.model.request.merchant.MerchantBillSearchRequest;
import com.miner.disco.basic.model.response.PageResponse;

/**
 * author:linjw Date:2019/1/14 Time:17:57
 */
public interface MerchantBillService {

    PageResponse merchantBillList(MerchantBillSearchRequest searchRequest) throws AdminBuzException;

}

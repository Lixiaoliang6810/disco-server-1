package com.miner.disco.admin.service.merchant.impl;

import com.miner.disco.admin.dao.merchant.MerchantBillDao;
import com.miner.disco.admin.exception.AdminBuzException;
import com.miner.disco.admin.model.request.merchant.MerchantBillSearchRequest;
import com.miner.disco.admin.model.response.merchant.MerchantBillListResponse;
import com.miner.disco.admin.service.merchant.MerchantBillService;
import com.miner.disco.basic.model.response.PageResponse;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * author:linjw Date:2019/1/14 Time:17:57
 */
@Service
public class MerchantBillServiceImpl implements MerchantBillService {

    @Autowired
    private MerchantBillDao merchantBillDao;

    @Override
    public PageResponse merchantBillList(MerchantBillSearchRequest searchRequest) throws AdminBuzException {
        List<MerchantBillListResponse> listResponses = merchantBillDao.billList(searchRequest);
        int total = merchantBillDao.countBillList(searchRequest);
        return new PageResponse(total, listResponses);
    }
}

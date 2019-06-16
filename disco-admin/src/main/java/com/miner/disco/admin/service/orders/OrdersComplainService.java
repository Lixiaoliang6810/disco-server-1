package com.miner.disco.admin.service.orders;

import com.miner.disco.admin.exception.AdminBuzException;
import com.miner.disco.basic.model.request.Pagination;
import com.miner.disco.basic.model.response.PageResponse;

/**
 * author:linjw Date:2019/1/7 Time:15:43
 */
public interface OrdersComplainService {

    /**
     * 获取投诉列表
     * @param page
     * @param no
     * @return
     * @throws AdminBuzException
     */
    PageResponse complainList(Pagination page,String no) throws AdminBuzException;
}

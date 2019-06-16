package com.miner.disco.admin.service.member;

import com.miner.disco.admin.exception.AdminBuzException;
import com.miner.disco.admin.model.request.member.MemberOrderPageRequest;
import com.miner.disco.basic.model.response.PageResponse;

/**
 * @author: chencx
 * @create: 2019-01-17
 **/
public interface MemberOrderService {

    /**
     * 分页查询用户订单
     * @param page
     * @return
     * @throws AdminBuzException
     */
    PageResponse findPage(MemberOrderPageRequest page) throws AdminBuzException;

}

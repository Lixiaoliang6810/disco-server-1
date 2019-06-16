package com.miner.disco.admin.service.member;

import com.miner.disco.admin.exception.AdminBuzException;
import com.miner.disco.admin.model.request.member.CollectSearchRequest;
import com.miner.disco.basic.model.response.PageResponse;

/**
 * author:linjw Date:2019/1/8 Time:14:39
 */
public interface CollectService {

    /**
     * 收藏列表
     * @param searchRequest
     * @return
     * @throws AdminBuzException
     */
    PageResponse collectList(CollectSearchRequest searchRequest) throws AdminBuzException;
}

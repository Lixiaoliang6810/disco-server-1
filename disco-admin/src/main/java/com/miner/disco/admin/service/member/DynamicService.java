package com.miner.disco.admin.service.member;

import com.miner.disco.admin.exception.AdminBuzException;
import com.miner.disco.admin.model.request.member.MemberDynamicSearchRequest;
import com.miner.disco.basic.model.response.PageResponse;

/**
 * author:linjw Date:2019/1/8 Time:10:27
 */
public interface DynamicService {

    /**
     * 用户动态列表
     * @param searchRequest
     * @return
     * @throws AdminBuzException
     */
    PageResponse dynamicList(MemberDynamicSearchRequest searchRequest) throws AdminBuzException;

    /**
     * 按ID删除
     * @param id
     * @throws AdminBuzException
     */
    void del(Long id) throws AdminBuzException;
}

package com.miner.disco.admin.service.member;

import com.miner.disco.admin.exception.AdminBuzException;
import com.miner.disco.basic.model.request.Pagination;
import com.miner.disco.basic.model.response.PageResponse;

/**
 * author:linjw Date:2019/1/7 Time:17:55
 */
public interface FollowRecordService {

    /**
     * 偶像列表
     * @param page
     * @param id
     * @return
     * @throws AdminBuzException
     */
    PageResponse idolList(Pagination page,Long id) throws AdminBuzException;

    /**
     * 粉丝列表
     * @param page
     * @param id
     * @return
     * @throws AdminBuzException
     */
    PageResponse fansList(Pagination page,Long id) throws AdminBuzException;
}

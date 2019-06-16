package com.miner.disco.admin.service.member;

import com.miner.disco.admin.exception.AdminBuzException;
import com.miner.disco.admin.model.request.member.MemberSearchRequest;
import com.miner.disco.admin.model.response.member.MemberDetailResponse;
import com.miner.disco.basic.model.response.PageResponse;

/**
 * author:linjw Date:2019/1/3 Time:16:52
 */
public interface MemberService {

    /**
     * 用户列表
     * @param searchRequest
     * @return
     * @throws AdminBuzException
     */
    PageResponse queryMemberList(MemberSearchRequest searchRequest) throws AdminBuzException;

    /**
     * 用户详情
     * @param id
     * @return
     * @throws AdminBuzException
     */
    MemberDetailResponse details(Long id) throws AdminBuzException;

    /**
     * 设置是否推荐
     * @param id
     * @param recommend
     * @throws AdminBuzException
     */
    void updateRecommend(Long id, Integer recommend) throws AdminBuzException;

    /**
     * 设置领队
     * @param id
     * @throws AdminBuzException
     */
    void updateLeader(Long id) throws AdminBuzException;
}

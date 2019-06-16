package com.miner.disco.admin.service.system;

import com.miner.disco.admin.exception.AdminBuzException;
import com.miner.disco.admin.model.request.system.BannerCreateRequest;
import com.miner.disco.admin.model.request.system.BannerModifyRequest;
import com.miner.disco.admin.model.request.system.BannerSearchRequest;
import com.miner.disco.basic.model.response.PageResponse;

/**
 * author:linjw Date:2019/1/7 Time:19:19
 */
public interface BannerService {

    /**
     * banner列表
     * @param search
     * @return
     * @throws AdminBuzException
     */
    PageResponse list(BannerSearchRequest search) throws AdminBuzException;

    /**
     * 新建banner
     * @param bannerCreateRequest
     * @throws AdminBuzException
     */
    void add(BannerCreateRequest bannerCreateRequest) throws AdminBuzException;

    /**
     * 修改banner
     * @param bannerModifyRequest
     * @throws AdminBuzException
     */
    void update(BannerModifyRequest bannerModifyRequest) throws AdminBuzException;

    /**
     * 删除banner
     * @param id
     * @throws AdminBuzException
     */
    void delete(Long id) throws AdminBuzException;
}

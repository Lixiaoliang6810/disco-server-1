package com.miner.disco.admin.service.system;

import com.miner.disco.admin.exception.AdminBuzException;
import com.miner.disco.admin.model.request.system.ConfigCreateRequest;
import com.miner.disco.admin.model.request.system.ConfigModifyRequest;
import com.miner.disco.basic.model.request.Pagination;
import com.miner.disco.basic.model.response.PageResponse;
import com.miner.disco.pojo.Config;

/**
 * author:linjw Date:2019/1/7 Time:19:57
 */
public interface ConfigService {

    /**
     * 配置列表
     * @param pagination
     * @return
     * @throws AdminBuzException
     */
    PageResponse configList(Pagination pagination) throws AdminBuzException;

    /**
     * 心中配置
     * @param configCreateRequest
     * @throws AdminBuzException
     */
    void addConfig(ConfigCreateRequest configCreateRequest) throws AdminBuzException;

    /**
     * 修改配置
     * @param configModifyRequest
     * @throws AdminBuzException
     */
    void update(ConfigModifyRequest configModifyRequest) throws AdminBuzException;

    /**
     * 配置详情
     * @param id
     * @return
     * @throws AdminBuzException
     */
    Config configDetail(Long id) throws AdminBuzException;
}

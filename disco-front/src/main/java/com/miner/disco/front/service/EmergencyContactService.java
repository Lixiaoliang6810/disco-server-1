package com.miner.disco.front.service;

import com.miner.disco.front.exception.BusinessException;
import com.miner.disco.front.model.response.EmergencyContactListResponse;

import java.util.List;

/**
 * @author Created by lubycoder@163.com 2018/12/24
 */
public interface EmergencyContactService {

    /**
     * 添加紧急联系人
     *
     * @param userId   用户主键
     * @param realname 姓名
     * @param mobile   手机号
     * @throws BusinessException
     */
    void create(Long userId, String realname, String mobile) throws BusinessException;

    /**
     * 删除紧急联系人
     *
     * @param userId 用户主键
     * @param id     紧急联系人主键
     * @throws BusinessException
     */
    void delete(Long userId, Long id) throws BusinessException;

    /**
     * 查询紧急联系人列表
     *
     * @param userId 用户主键
     * @return
     * @throws BusinessException
     */
    List<EmergencyContactListResponse> list(Long userId) throws BusinessException;

}

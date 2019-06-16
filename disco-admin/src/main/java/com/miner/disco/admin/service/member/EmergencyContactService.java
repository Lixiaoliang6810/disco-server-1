package com.miner.disco.admin.service.member;

import com.miner.disco.admin.exception.AdminBuzException;
import com.miner.disco.admin.model.request.member.EmergencyContactSearchRequest;
import com.miner.disco.basic.model.response.PageResponse;

/**
 * author:linjw Date:2019/1/8 Time:11:01
 */
public interface EmergencyContactService {

    /**
     * 用户紧急联系人列表
     * @param searchRequest
     * @return
     * @throws AdminBuzException
     */
    PageResponse emergencyContactList(EmergencyContactSearchRequest searchRequest) throws AdminBuzException;
}

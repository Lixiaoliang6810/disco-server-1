package com.miner.disco.front.service.impl;


import com.miner.disco.basic.constants.DeleteStatus;
import com.miner.disco.front.dao.EmergencyContactMapper;
import com.miner.disco.front.exception.BusinessException;
import com.miner.disco.front.exception.BusinessExceptionCode;
import com.miner.disco.front.model.response.EmergencyContactListResponse;
import com.miner.disco.pojo.EmergencyContact;
import com.miner.disco.front.service.EmergencyContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @author Created by lubycoder@163.com 2018/12/24
 */
@Service
public class EmergencyContactServiceImpl implements EmergencyContactService {

    @Autowired
    private EmergencyContactMapper emergencyContactMapper;

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void create(Long userId, String realname, String mobile) throws BusinessException {
        // TODO: 2018/12/24 最多添加5个
        EmergencyContact emergencyContact = new EmergencyContact();
        emergencyContact.setMobile(mobile);
        emergencyContact.setRealname(realname);
        emergencyContact.setUserId(userId);
        emergencyContact.setCreateDate(new Date());
        emergencyContact.setUpdateDate(new Date());
        emergencyContact.setDeleted(DeleteStatus.NORMAL.getKey());
        emergencyContactMapper.insert(emergencyContact);
    }

    @Override
    public void delete(Long userId, Long id) throws BusinessException {
        EmergencyContact emergencyContact = emergencyContactMapper.queryByPrimaryKey(id);
        if (emergencyContact == null || emergencyContact.getUserId().longValue() != userId) {
            throw new BusinessException(BusinessExceptionCode.ILLEGAL_OPERATION.getCode(), "非法操作");
        }
        emergencyContact.setDeleted(DeleteStatus.DELETE.getKey());
        emergencyContactMapper.updateByPrimaryKey(emergencyContact);
    }

    @Override
    public List<EmergencyContactListResponse> list(Long userId) throws BusinessException {
        return emergencyContactMapper.queryByUserId(userId);
    }
}

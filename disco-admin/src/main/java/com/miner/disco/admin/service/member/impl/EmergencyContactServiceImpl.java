package com.miner.disco.admin.service.member.impl;

import com.miner.disco.admin.dao.member.EmergencyContactDao;
import com.miner.disco.admin.exception.AdminBuzException;
import com.miner.disco.admin.model.request.member.EmergencyContactSearchRequest;
import com.miner.disco.admin.model.response.member.EmergencyContactResponse;
import com.miner.disco.admin.service.member.EmergencyContactService;
import com.miner.disco.basic.model.response.PageResponse;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * author:linjw Date:2019/1/8 Time:11:02
 */
@Service
public class EmergencyContactServiceImpl implements EmergencyContactService {

    @Autowired
    private EmergencyContactDao emergencyContactDao;

    @Override
    public PageResponse emergencyContactList(EmergencyContactSearchRequest searchRequest) throws AdminBuzException {
        List<EmergencyContactResponse> list = emergencyContactDao.emergencyContactList(searchRequest);
        int total = emergencyContactDao.countList(searchRequest);
        return new PageResponse(total, list);
    }
}

package com.miner.disco.admin.dao.member;

import com.miner.disco.admin.model.request.member.EmergencyContactSearchRequest;
import com.miner.disco.admin.model.response.member.EmergencyContactResponse;
import java.util.List;

/**
 * author:linjw Date:2019/1/8 Time:10:38
 */
public interface EmergencyContactDao {

    List<EmergencyContactResponse> emergencyContactList(EmergencyContactSearchRequest search);

    int countList(EmergencyContactSearchRequest search);
}

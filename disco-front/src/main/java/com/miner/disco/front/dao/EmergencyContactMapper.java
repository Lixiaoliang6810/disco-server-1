package com.miner.disco.front.dao;

import com.miner.disco.front.model.response.EmergencyContactListResponse;
import com.miner.disco.pojo.EmergencyContact;
import com.miner.disco.mybatis.support.BasicMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Created by lubycoder@163.com 2018/12/24
 */
public interface EmergencyContactMapper extends BasicMapper<EmergencyContact> {

    List<EmergencyContactListResponse> queryByUserId(@Param("userId") Long uid);

}

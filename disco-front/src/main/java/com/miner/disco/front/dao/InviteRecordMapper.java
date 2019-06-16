package com.miner.disco.front.dao;

import com.miner.disco.front.model.request.InviteRecordListRequest;
import com.miner.disco.front.model.response.InviteRecordListResponse;
import com.miner.disco.pojo.InviteRecord;
import com.miner.disco.mybatis.support.BasicMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Created by lubycoder@163.com 2018/12/24
 */
public interface InviteRecordMapper extends BasicMapper<InviteRecord> {

    List<InviteRecordListResponse> queryByUserId(InviteRecordListRequest request);

    Integer queryLevelByInviteeId(@Param("inviteeId") Long inviteeId);

}

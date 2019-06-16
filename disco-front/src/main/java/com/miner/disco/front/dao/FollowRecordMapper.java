package com.miner.disco.front.dao;

import com.miner.disco.front.model.response.FollowListResponse;
import com.miner.disco.pojo.FollowRecord;
import com.miner.disco.mybatis.support.BasicMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Created by lubycoder@163.com 2018/12/24
 */
public interface FollowRecordMapper extends BasicMapper<FollowRecord> {

    List<FollowListResponse> queryByUserId(@Param("userId") Long userId);

}

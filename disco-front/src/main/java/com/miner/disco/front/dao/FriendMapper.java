package com.miner.disco.front.dao;

import com.miner.disco.front.model.request.FriendListRequest;
import com.miner.disco.front.model.response.FriendListResponse;
import com.miner.disco.mybatis.support.BasicMapper;
import com.miner.disco.pojo.Friend;
import org.apache.ibatis.annotations.Param;
import org.springframework.security.core.parameters.P;

import java.util.List;

/**
 * @author Created by lubycoder@163.com 2019/1/3
 */
public interface FriendMapper extends BasicMapper<Friend> {

    List<FriendListResponse> queryByUserId(FriendListRequest request);

    Integer countByUserId(Long userId);

    Friend queryByOwnAndHim(@Param("ownUserId") Long ownUserId, @Param("himUserId") Long himUserId);

}

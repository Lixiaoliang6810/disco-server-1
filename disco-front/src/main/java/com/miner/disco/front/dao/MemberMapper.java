package com.miner.disco.front.dao;

import com.miner.disco.front.model.request.VipMemberListRequest;
import com.miner.disco.front.model.response.VipMemberListResponse;
import com.miner.disco.pojo.Member;
import com.miner.disco.mybatis.support.BasicMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Created by lubycoder@163.com 2018/12/24
 */
public interface MemberMapper extends BasicMapper<Member> {

    Member queryByMobile(String mobile);

    Member queryByPrimaryKeyForUpdate(@Param("id") Long id);

    List<VipMemberListResponse> queryByVip(VipMemberListRequest request);

    Member queryByImAccount(@Param("imAccount") String imAccount);

    Long queryUser(String name);

}

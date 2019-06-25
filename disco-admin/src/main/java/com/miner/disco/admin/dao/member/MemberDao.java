package com.miner.disco.admin.dao.member;

import com.miner.disco.admin.model.request.member.MemberSearchRequest;
import com.miner.disco.admin.model.response.member.MemberListResponse;
import com.miner.disco.mybatis.support.BasicMapper;
import com.miner.disco.pojo.Member;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * author:linjw Date:2019/1/3 Time:16:52
 */
public interface MemberDao extends BasicMapper<Member> {

    List<MemberListResponse> memberList(@Param("search") MemberSearchRequest search);

    int countMember(@Param("search") MemberSearchRequest search);

    Member queryByPrimaryKeyForUpdate(@Param("id") Long id);
}

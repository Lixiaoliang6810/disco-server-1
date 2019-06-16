package com.miner.disco.admin.dao.member;

import com.miner.disco.admin.model.request.member.MemberDynamicSearchRequest;
import com.miner.disco.admin.model.response.member.MemberDynamicListResponse;
import com.miner.disco.mybatis.support.BasicMapper;
import com.miner.disco.pojo.Dynamic;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * author:linjw Date:2019/1/8 Time:10:02
 */
public interface DynamicDao extends BasicMapper<Dynamic> {

    List<MemberDynamicListResponse> queryMemberDynamicPage(@Param("search") MemberDynamicSearchRequest search);

    int countDynamicPage(@Param("search") MemberDynamicSearchRequest search);

}

package com.miner.disco.admin.dao.member;

import com.miner.disco.admin.model.request.member.VipApplySearchRequest;
import com.miner.disco.admin.model.response.member.VipApplyDetailsResponse;
import com.miner.disco.admin.model.response.member.VipApplyListResponse;
import com.miner.disco.mybatis.support.BasicMapper;
import com.miner.disco.pojo.VipApply;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * author:linjw Date:2019/1/7 Time:16:49
 */
public interface VipApplyDao extends BasicMapper<VipApply> {

    List<VipApplyListResponse> list(@Param("search") VipApplySearchRequest search);

    int countList(@Param("search") VipApplySearchRequest search);

    VipApplyDetailsResponse details(@Param("id") Long id);

    VipApply queryByPrimaryKeyForUpdate(@Param("id") Long id);

}

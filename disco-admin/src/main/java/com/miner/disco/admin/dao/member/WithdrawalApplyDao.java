package com.miner.disco.admin.dao.member;

import com.miner.disco.admin.model.request.member.WithdrawalApplyRequest;
import com.miner.disco.admin.model.response.member.WithdrawalApplyResponse;
import com.miner.disco.mybatis.support.BasicMapper;
import com.miner.disco.pojo.WithdrawalApply;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author: chencx
 * @create: 2019-01-17
 **/
public interface WithdrawalApplyDao extends BasicMapper<WithdrawalApply> {

    List<WithdrawalApplyResponse> queryPage(@Param("search") WithdrawalApplyRequest serach);

    Integer queryPageCount(@Param("search") WithdrawalApplyRequest search);

    WithdrawalApply queryByPrimaryKeyForUpdate(@Param("id") Long id);
}

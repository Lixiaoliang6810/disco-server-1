package com.miner.disco.front.dao;

import com.miner.disco.pojo.VipApply;
import com.miner.disco.mybatis.support.BasicMapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author Created by lubycoder@163.com 2018/12/24
 */
public interface VipApplyMapper extends BasicMapper<VipApply> {

    VipApply queryLastApplyByUserId(@Param("userId") Long userId);

}

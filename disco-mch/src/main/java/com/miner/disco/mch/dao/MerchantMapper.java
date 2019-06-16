package com.miner.disco.mch.dao;

import com.miner.disco.mybatis.support.BasicMapper;
import com.miner.disco.pojo.Merchant;
import org.apache.ibatis.annotations.Param;

/**
 * @author Created by lubycoder@163.com 2019/1/8
 */
public interface MerchantMapper extends BasicMapper<Merchant> {

    Merchant queryByMobile(@Param("mobile") String mobile);

    Merchant queryByPrimaryKeyForUpdate(@Param("id") Long id);

}

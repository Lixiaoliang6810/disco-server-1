package com.miner.disco.mch.dao;

import com.miner.disco.mybatis.support.BasicMapper;
import com.miner.disco.pojo.CallbackNotify;
import org.apache.ibatis.annotations.Param;

/**
 * @author Created by lubycoder@163.com 2019-06-21
 */
public interface CallbackNotifyMapper extends BasicMapper<CallbackNotify> {

    CallbackNotify queryBySnAndType(@Param("sn") String sn, @Param("type") Integer type);

}


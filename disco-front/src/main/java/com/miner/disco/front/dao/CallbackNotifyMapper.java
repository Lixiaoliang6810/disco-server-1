package com.miner.disco.front.dao;

import com.miner.disco.pojo.CallbackNotify;
import com.miner.disco.mybatis.support.BasicMapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author Created by lubycoder@163.com 2018/12/24
 */
public interface CallbackNotifyMapper extends BasicMapper<CallbackNotify> {

    CallbackNotify queryBySnAndType(@Param("sn") String sn, @Param("type") Integer type);

}

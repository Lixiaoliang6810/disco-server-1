package com.miner.disco.front.dao;

import com.miner.disco.mybatis.support.BasicMapper;
import com.miner.disco.pojo.Config;
import org.apache.ibatis.annotations.Param;

/**
 * @author Created by lubycoder@163.com 2019-2-21
 */
public interface ConfigMapper extends BasicMapper<Config> {

    Config queryByKey(@Param("key") String key);

}

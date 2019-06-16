package com.miner.disco.mch.dao;

import com.miner.disco.mch.model.response.ClassifySelectorResponse;
import com.miner.disco.mybatis.support.BasicMapper;
import com.miner.disco.pojo.Classify;

import java.util.List;

/**
 * @author Created by lubycoder@163.com 2019-2-19
 */
public interface ClassifyMapper extends BasicMapper<Classify> {

    List<ClassifySelectorResponse> queryAllEffective();

}

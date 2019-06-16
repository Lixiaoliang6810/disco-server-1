package com.miner.disco.front.dao;

import com.miner.disco.front.model.response.ClassifyListResponse;
import com.miner.disco.mybatis.support.BasicMapper;

import java.util.List;

/**
 * @author Created by lubycoder@163.com 2018/12/24
 */
public interface ClassifyMapper extends BasicMapper<ClassifyMapper> {

    List<ClassifyListResponse> queryAllEffective();

}

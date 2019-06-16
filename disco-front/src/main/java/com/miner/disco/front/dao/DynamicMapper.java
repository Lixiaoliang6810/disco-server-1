package com.miner.disco.front.dao;


import com.miner.disco.front.model.request.DynamicsListRequest;
import com.miner.disco.front.model.response.DynamicsListResponse;
import com.miner.disco.pojo.Dynamic;
import com.miner.disco.mybatis.support.BasicMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Created by lubycoder@163.com 2018/12/24
 */
public interface DynamicMapper extends BasicMapper<Dynamic> {

    List<DynamicsListResponse> queryList(DynamicsListRequest request);

    List<String> queryImagesByUserId(@Param("userId") Long userId);

}

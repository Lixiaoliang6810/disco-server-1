package com.miner.disco.admin.dao.system;

import com.miner.disco.admin.model.response.system.ConfigListResponse;
import com.miner.disco.basic.model.request.Pagination;
import com.miner.disco.mybatis.support.BasicMapper;
import com.miner.disco.pojo.Config;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * author:linjw Date:2019/1/7 Time:19:50
 */
public interface ConfigDao extends BasicMapper<Config> {

    List<ConfigListResponse> configList(@Param(value = "page") Pagination pagination);

    int countConfigList();

}

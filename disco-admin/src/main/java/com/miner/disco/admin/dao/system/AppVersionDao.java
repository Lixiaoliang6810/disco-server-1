package com.miner.disco.admin.dao.system;

import com.miner.disco.admin.model.request.system.AppVersionSearchRequest;
import com.miner.disco.admin.model.response.system.AppVersionListResponse;
import com.miner.disco.mybatis.support.BasicMapper;
import com.miner.disco.pojo.AppVersion;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * author:linjw Date:2019/2/19 Time:11:12
 */
public interface AppVersionDao extends BasicMapper<AppVersion> {

    List<AppVersionListResponse> versionList(@Param(value = "search") AppVersionSearchRequest search);

    int countVersion(@Param(value = "search")AppVersionSearchRequest search);
}

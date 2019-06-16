package com.miner.disco.front.dao;

import com.miner.disco.front.model.request.AppVersionCheckRequest;
import com.miner.disco.mybatis.support.BasicMapper;
import com.miner.disco.pojo.AppVersion;

/**
 * @author Created by lubycoder@163.com 2019-2-14
 */
public interface AppVersionMapper extends BasicMapper<AppVersion> {

    AppVersion queryLatestByAppAndType(AppVersionCheckRequest request);

}

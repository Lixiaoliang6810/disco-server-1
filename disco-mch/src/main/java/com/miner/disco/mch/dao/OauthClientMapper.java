package com.miner.disco.mch.dao;

import com.miner.disco.pojo.OauthClient;
import org.apache.ibatis.annotations.Param;

/**
 * @author Created by lubycoder@163.com 2018/12/26
 */
public interface OauthClientMapper {

    OauthClient queryByClientId(@Param("clientId") String clientId);

}

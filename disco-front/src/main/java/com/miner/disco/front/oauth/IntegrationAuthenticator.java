package com.miner.disco.front.oauth;

import com.miner.disco.pojo.Member;

/**
 * @author Created by lubycoder@163.com 2019/1/8
 */
public interface IntegrationAuthenticator {

    Member authenticate(IntegrationAuthentication integrationAuthentication);

    void prepare(IntegrationAuthentication integrationAuthentication);

    void complete(IntegrationAuthentication integrationAuthentication);

    boolean support(IntegrationAuthentication integrationAuthentication);

}

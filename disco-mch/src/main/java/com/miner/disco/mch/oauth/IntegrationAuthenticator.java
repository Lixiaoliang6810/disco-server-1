package com.miner.disco.mch.oauth;

import com.miner.disco.pojo.Merchant;

/**
 * @author Created by lubycoder@163.com 2019/1/8
 */
public interface IntegrationAuthenticator {

    Merchant authenticate(IntegrationAuthentication integrationAuthentication);

    void prepare(IntegrationAuthentication integrationAuthentication);

    void complete(IntegrationAuthentication integrationAuthentication);

    boolean support(IntegrationAuthentication integrationAuthentication);

}

package com.miner.disco.mch.service;

import org.springframework.security.oauth2.provider.ClientDetails;

/**
 * @author Created by lubycoder@163.com 2018/12/26
 */
public interface OauthClientService {

    ClientDetails loadClientByClientId(String clientId);

}

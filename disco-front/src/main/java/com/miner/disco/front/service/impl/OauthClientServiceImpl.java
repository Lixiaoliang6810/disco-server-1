package com.miner.disco.front.service.impl;

import com.miner.disco.front.dao.OauthClientMapper;
import com.miner.disco.pojo.OauthClient;
import com.miner.disco.front.service.OauthClientService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.NoSuchClientException;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;

/**
 * @author Created by lubycoder@163.com 2018/12/26
 */
@Service
public class OauthClientServiceImpl implements OauthClientService {

    @Autowired
    private OauthClientMapper oauthClientMapper;

    @Override
    public ClientDetails loadClientByClientId(String clientId) {
        OauthClient oauthClient = oauthClientMapper.queryByClientId(clientId);
        if (oauthClient == null) {
            throw new NoSuchClientException("client id " + clientId + " not found");
        }

        BaseClientDetails clientDetails = new BaseClientDetails();
        clientDetails.setClientId(oauthClient.getClientId());
        clientDetails.setClientSecret(oauthClient.getClientSecret());
        clientDetails.setAccessTokenValiditySeconds(oauthClient.getAccessTokenValidity());
        clientDetails.setRefreshTokenValiditySeconds(oauthClient.getRefreshTokenValidity());
        clientDetails.setAuthorizedGrantTypes(Arrays.asList(StringUtils.split(oauthClient.getGrantTypes(), ",")));
        clientDetails.setScope(Arrays.asList(StringUtils.split(oauthClient.getScope(), ",")));
        if (StringUtils.isNotBlank(oauthClient.getRedirectUri())) {
            clientDetails.setRegisteredRedirectUri(new HashSet<>(Arrays.asList(StringUtils.split(oauthClient.getRedirectUri(), ","))));
        }
        if (StringUtils.isNotBlank(oauthClient.getResourceIds())) {
            clientDetails.setRegisteredRedirectUri(new HashSet<>(Arrays.asList(StringUtils.split(oauthClient.getResourceIds(), ","))));
        }
        return clientDetails;
    }
}

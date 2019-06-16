package com.miner.disco.mch.oauth;

import com.miner.disco.mch.dao.MerchantMapper;
import com.miner.disco.pojo.Merchant;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

/**
 * @author Created by lubycoder@163.com 2019/1/8
 */
@Slf4j
@Primary
@Component
public class UsernamePasswordAuthenticator extends AbstractPreparableIntegrationAuthenticator {

    private final static String SMS_AUTH_TYPE = "pwd";

    @Autowired
    private MerchantMapper merchantMapper;

    @Override
    public Merchant authenticate(IntegrationAuthentication integrationAuthentication) {
        log.info("password auth authenticate.........");
        return merchantMapper.queryByMobile(integrationAuthentication.getUsername());
    }

    @Override
    public void prepare(IntegrationAuthentication integrationAuthentication) {
        log.info("password auth prepare.........");
    }

    @Override
    public boolean support(IntegrationAuthentication integrationAuthentication) {
        return StringUtils.equals(SMS_AUTH_TYPE, integrationAuthentication.getGrantType());
    }
}

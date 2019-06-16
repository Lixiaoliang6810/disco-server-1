package com.miner.disco.mch.oauth.service;

import com.miner.disco.mch.dao.MerchantMapper;
import com.miner.disco.mch.oauth.IntegrationAuthentication;
import com.miner.disco.mch.oauth.IntegrationAuthenticationContext;
import com.miner.disco.mch.oauth.IntegrationAuthenticator;
import com.miner.disco.mch.oauth.model.CustomUserDetails;
import com.miner.disco.pojo.Merchant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Created by lubycoder@163.com 2019/1/8
 */
@Component
public class IntegrationUserDetailsService implements UserDetailsService {

    private List<IntegrationAuthenticator> authenticators;

    @Autowired
    private MerchantMapper merchantMapper;

    @Autowired(required = false)
    public void setIntegrationAuthenticators(List<IntegrationAuthenticator> authenticators) {
        this.authenticators = authenticators;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        IntegrationAuthentication integrationAuthentication = IntegrationAuthenticationContext.get();
        //判断是否是集成登录
        if (integrationAuthentication == null) {
            integrationAuthentication = new IntegrationAuthentication();
        }
        integrationAuthentication.setUsername(username);
        Merchant merchant = this.authenticate(integrationAuthentication);       //merchantMapper.queryByMobile(username) ;
        if (merchant == null) {
            throw new UsernameNotFoundException("用户名或密码错误");
        }

        return new CustomUserDetails(merchant.getId(), merchant.getMobile(), merchant.getPassword(), merchant.getStatus());
    }

    private Merchant authenticate(IntegrationAuthentication integrationAuthentication) {
        if (this.authenticators != null) {
            for (IntegrationAuthenticator authenticator : authenticators) {
                if (authenticator.support(integrationAuthentication)) {
                    return authenticator.authenticate(integrationAuthentication);
                }
            }
        }
        return null;
    }

}

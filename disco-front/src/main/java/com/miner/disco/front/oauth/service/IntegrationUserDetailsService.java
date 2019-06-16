package com.miner.disco.front.oauth.service;

import com.miner.disco.front.oauth.IntegrationAuthentication;
import com.miner.disco.front.oauth.IntegrationAuthenticationContext;
import com.miner.disco.front.oauth.IntegrationAuthenticator;
import com.miner.disco.front.oauth.model.CustomUserDetails;
import com.miner.disco.front.service.MemberService;
import com.miner.disco.pojo.Member;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Created by lubycoder@163.com 2019/1/8
 */
@Component
public class IntegrationUserDetailsService implements UserDetailsService {

    @Autowired
    private MemberService memberService;

    private List<IntegrationAuthenticator> authenticators;

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

        Member member = this.authenticate(integrationAuthentication);
        if (member == null || StringUtils.isBlank(member.getLoginPassword())) {
            throw new UsernameNotFoundException("用户名或密码错误");
        }
        return new CustomUserDetails(member.getId(), member.getMobile(), member.getLoginPassword(),
                member.getImAccount(), member.getImPassword());
    }

    private Member authenticate(IntegrationAuthentication integrationAuthentication) {
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

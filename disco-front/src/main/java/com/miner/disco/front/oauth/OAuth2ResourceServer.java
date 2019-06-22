package com.miner.disco.front.oauth;

import com.miner.disco.front.oauth.exception.AuthExceptionEntryPoint;
import com.miner.disco.front.oauth.exception.CustomAccessDeniedHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.error.OAuth2AccessDeniedHandler;

/**
 * @author Created by lubycoder@163.com 2018/12/26
 */
@Configuration
@EnableResourceServer
public class OAuth2ResourceServer extends ResourceServerConfigurerAdapter {

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/oauth/**", "/sms/code", "/member/register", "/error").permitAll()
                .antMatchers("/index","/merchant/**","/member/vip/list","/member/ta/center").permitAll()
                .antMatchers("/orders/assemble/list").permitAll()
                .antMatchers("/dynamic/list","/dynamic/photos").permitAll()
                .antMatchers("/member/login/password/reset", "/alipay/orders/notify").permitAll()
                .antMatchers("/wxpay/orders/notify", "/version/check", "/alipay/sweep/notify", "/wxpay/sweep/notify").permitAll()

                .anyRequest().authenticated()
                .and().formLogin().permitAll()
                .and().logout().permitAll()
                .and().exceptionHandling().accessDeniedHandler(new OAuth2AccessDeniedHandler());
    }

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
        resources.authenticationEntryPoint(new AuthExceptionEntryPoint());
        resources.accessDeniedHandler(new CustomAccessDeniedHandler());
    }


}

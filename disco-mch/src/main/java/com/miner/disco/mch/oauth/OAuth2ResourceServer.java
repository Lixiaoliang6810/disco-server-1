package com.miner.disco.mch.oauth;

import com.miner.disco.mch.oauth.exception.AuthExceptionEntryPoint;
import com.miner.disco.mch.oauth.exception.CustomAccessDeniedHandler;
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
        http
                .authorizeRequests()
                .antMatchers("/oauth/**", "/sms/code", "/error").permitAll()
                .antMatchers("/merchant/register", "/version/check", "/oss/authorize").permitAll()
                .antMatchers("/classify/selector", "/merchant/apply", "/merchant/password/reset").permitAll()
                .antMatchers("/aggregate/alipay/sweep/notify").permitAll()
                .antMatchers("/merchant/receivables/qrcode").permitAll()
                .anyRequest().authenticated()
                .and().formLogin().permitAll()
                .and().logout().permitAll()
                .and().exceptionHandling().accessDeniedHandler(new OAuth2AccessDeniedHandler())
                .and().csrf().disable();
    }

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
        resources.authenticationEntryPoint(new AuthExceptionEntryPoint());
        resources.accessDeniedHandler(new CustomAccessDeniedHandler());
    }


}

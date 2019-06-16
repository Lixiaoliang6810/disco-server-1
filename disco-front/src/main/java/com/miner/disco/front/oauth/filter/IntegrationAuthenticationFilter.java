package com.miner.disco.front.oauth.filter;

import com.miner.disco.basic.model.response.ViewData;
import com.miner.disco.front.exception.BusinessException;
import com.miner.disco.front.oauth.IntegrationAuthentication;
import com.miner.disco.front.oauth.IntegrationAuthenticationContext;
import com.miner.disco.front.oauth.IntegrationAuthenticator;
import com.miner.disco.netease.support.JsonParser;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

/**
 * @author Created by lubycoder@163.com 2019/1/8
 */
@Component
@Order(-9)
public class IntegrationAuthenticationFilter extends GenericFilterBean implements ApplicationContextAware {

    private static final String AUTH_TYPE_PARAM_NAME = "auth_type";
    private static final String OAUTH_TOKEN_URL = "/oauth/token";
    private RequestMatcher requestMatcher;
    private ApplicationContext applicationContext;
    private Collection<IntegrationAuthenticator> authenticators;

    public IntegrationAuthenticationFilter() {
        this.requestMatcher = new OrRequestMatcher(
                new AntPathRequestMatcher(OAUTH_TOKEN_URL, HttpMethod.GET.name()),
                new AntPathRequestMatcher(OAUTH_TOKEN_URL, HttpMethod.POST.name())
        );
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        if (!requestMatcher.matches(request)) {
            filterChain.doFilter(request, response);
            return;
        }

        IntegrationAuthentication integrationAuthentication = new IntegrationAuthentication();
        integrationAuthentication.setGrantType(request.getParameter(AUTH_TYPE_PARAM_NAME));
        integrationAuthentication.setUsername(request.getParameter("username"));
        integrationAuthentication.setAuthParameters(request.getParameterMap());
        IntegrationAuthenticationContext.set(integrationAuthentication);

        try {
            this.prepare(integrationAuthentication);
            filterChain.doFilter(request, response);
            this.complete(integrationAuthentication);
        } catch (BusinessException be) {
            response.setContentType("application/json;charset=utf-8");
            OutputStream os = response.getOutputStream();
            ViewData viewData = ViewData.builder().status(be.getCode()).message(be.getMessage()).build();
            os.write(JsonParser.serializeToJson(viewData).getBytes(Charset.forName("UTF-8")));
            os.flush();
            os.close();
        } finally {
            IntegrationAuthenticationContext.clear();
        }

    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    /**
     * 前置处理
     */
    private void prepare(IntegrationAuthentication integrationAuthentication) {
        //延迟加载认证器
        if (this.authenticators == null) {
            synchronized (this) {
                Map<String, IntegrationAuthenticator> integrationAuthenticatorMap = applicationContext.getBeansOfType(IntegrationAuthenticator.class);
                this.authenticators = integrationAuthenticatorMap.values();
            }
        }

        for (IntegrationAuthenticator authenticator : authenticators) {
            if (authenticator.support(integrationAuthentication)) {
                authenticator.prepare(integrationAuthentication);
            }
        }
    }

    /**
     * 后置处理
     */
    private void complete(IntegrationAuthentication integrationAuthentication) {
        for (IntegrationAuthenticator authenticator : authenticators) {
            if (authenticator.support(integrationAuthentication)) {
                authenticator.complete(integrationAuthentication);
            }
        }
    }
}


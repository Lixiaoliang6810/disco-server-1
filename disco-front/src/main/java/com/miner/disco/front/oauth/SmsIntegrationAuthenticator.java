package com.miner.disco.front.oauth;

import com.miner.disco.front.consts.Const;
import com.miner.disco.front.exception.BusinessException;
import com.miner.disco.front.exception.BusinessExceptionCode;
import com.miner.disco.front.oauth.event.SmsAuthenticateBeforeEvent;
import com.miner.disco.front.oauth.exception.CustomOauthException;
import com.miner.disco.front.service.MemberService;
import com.miner.disco.pojo.Member;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author Created by lubycoder@163.com 2019/1/8
 */
@Slf4j
@Component
public class SmsIntegrationAuthenticator extends AbstractPreparableIntegrationAuthenticator implements ApplicationEventPublisherAware {

    @Autowired
    private MemberService memberService;

    private ApplicationEventPublisher applicationEventPublisher;

    @Resource(name = "redisTemplate")
    private ValueOperations<String, String> vOps;

    private final static String SMS_AUTH_TYPE = "sms";

    @Override
    public Member authenticate(IntegrationAuthentication integrationAuthentication) {
        log.info("sms auth authenticate.........");
        String password = integrationAuthentication.getAuthParameter("password");
        SmsAuthenticateBeforeEvent beforeEvent = new SmsAuthenticateBeforeEvent(this);
        beforeEvent.setMobile(integrationAuthentication.getUsername());
        beforeEvent.setCode(password);
        this.applicationEventPublisher.publishEvent(beforeEvent);
        Member member = this.memberService.findUserByUsername(integrationAuthentication.getUsername());
        if (member != null) {
            member.setLoginPassword(PasswordEncoderFactories.createDelegatingPasswordEncoder().encode(password));
        }
        return member;
    }

    @Override
    public void prepare(IntegrationAuthentication integrationAuthentication) {
        log.info("sms auth prepare.........");
        String cacheCode = vOps.get(String.format(Const.REDIS_CACHE_SMS_PREFIX, integrationAuthentication.getUsername()));
        String paramCode = integrationAuthentication.getAuthParameter("password");
        if (StringUtils.isBlank(cacheCode) || !StringUtils.equals(cacheCode, paramCode)) {
            throw new BusinessException(BusinessExceptionCode.SMS_CODE_ERROR.getCode(), "验证码错误或已过期");
        }
    }

    @Override
    public boolean support(IntegrationAuthentication integrationAuthentication) {
        return StringUtils.equals(SMS_AUTH_TYPE, integrationAuthentication.getGrantType());
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }
}

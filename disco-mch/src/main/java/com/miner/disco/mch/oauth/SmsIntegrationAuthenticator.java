package com.miner.disco.mch.oauth;

import com.miner.disco.mch.consts.Const;
import com.miner.disco.mch.dao.MerchantMapper;
import com.miner.disco.mch.exception.MchBusinessException;
import com.miner.disco.mch.exception.MchBusinessExceptionCode;
import com.miner.disco.mch.oauth.event.SmsAuthenticateBeforeEvent;
import com.miner.disco.pojo.Merchant;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author Created by lubycoder@163.com 2019/1/8
 */
@Slf4j
@Component
public class SmsIntegrationAuthenticator extends AbstractPreparableIntegrationAuthenticator implements ApplicationEventPublisherAware {

    @Autowired
    private MerchantMapper merchantMapper;

    private ApplicationEventPublisher applicationEventPublisher;

    @Resource(name = "redisTemplate")
    private ValueOperations<String, String> vOps;

    private final static String SMS_AUTH_TYPE = "sms";

    @Override
    public Merchant authenticate(IntegrationAuthentication integrationAuthentication) {
        log.info("sms auth authenticate.........");
        String password = integrationAuthentication.getAuthParameter("password");
        SmsAuthenticateBeforeEvent beforeEvent = new SmsAuthenticateBeforeEvent(this);
        beforeEvent.setMobile(integrationAuthentication.getUsername());
        beforeEvent.setCode(password);
        this.applicationEventPublisher.publishEvent(beforeEvent);
        Merchant merchant = this.merchantMapper.queryByMobile(integrationAuthentication.getUsername());
        if (merchant != null) {
            merchant.setPassword(PasswordEncoderFactories.createDelegatingPasswordEncoder().encode(password));
        }
        return merchant;
    }

    @Override
    public void prepare(IntegrationAuthentication integrationAuthentication) {
        log.info("sms auth prepare.........");
        String cacheCode = vOps.get(String.format(Const.REDIS_CACHE_SMS_PREFIX, integrationAuthentication.getUsername()));
        String paramCode = integrationAuthentication.getAuthParameter("password");
        if (StringUtils.isBlank(cacheCode) || !StringUtils.equals(cacheCode, paramCode)) {
            throw new MchBusinessException(MchBusinessExceptionCode.SMS_CODE_ERROR.getCode(), "验证码错误或已过期");
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

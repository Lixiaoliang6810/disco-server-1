package com.miner.disco.front.oauth.exception;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;

/**
 * @author Created by lubycoder@163.com 2019/1/10
 */
@JsonSerialize(using = CustomOauthExceptionSerializer.class)
public class CustomOauthException extends OAuth2Exception {
    private static final long serialVersionUID = -4545306381297861180L;

    public CustomOauthException(String msg) {
        super(msg);
    }
}

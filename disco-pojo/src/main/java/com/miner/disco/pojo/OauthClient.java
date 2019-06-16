package com.miner.disco.pojo;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Created by lubycoder@163.com 2018/12/26
 */
@Getter
@Setter
public class OauthClient {

    private String clientId;
    private String resourceIds;
    private String clientSecret;
    private String scope;
    private String grantTypes;
    private String redirectUri;
    private String authorities;
    private Integer accessTokenValidity;
    private Integer refreshTokenValidity;
    private String additionalInformation;
    private String autoapprove;

}

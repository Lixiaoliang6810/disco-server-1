package com.miner.disco.mch.oauth;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

/**
 * @author Created by lubycoder@163.com 2019/1/8
 */
@Getter
@Setter
public class IntegrationAuthentication {

    private String grantType;
    private String username;
    private Map<String,String[]> authParameters;

    public String getAuthParameter(String paramter){
        String[] values = this.authParameters.get(paramter);
        if(values != null && values.length > 0){
            return values[0];
        }
        return null;
    }

}

package com.miner.disco.mch.model.request;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author Created by lubycoder@163.com 2019/1/9
 */
@Getter
@Setter
public class MerchantRegisterRequest implements Serializable {

    private static final long serialVersionUID = -4312764327372624572L;

    private String mobile;
    private String digit;
    private String password;
    
}

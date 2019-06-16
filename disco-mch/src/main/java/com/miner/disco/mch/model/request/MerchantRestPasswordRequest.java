package com.miner.disco.mch.model.request;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author Created by lubycoder@163.com 2019/1/14
 */
@Getter
@Setter
public class MerchantRestPasswordRequest implements Serializable {

    private static final long serialVersionUID = -320435977491673809L;

    private String mobile;
    private String digit;
    private String password;

}

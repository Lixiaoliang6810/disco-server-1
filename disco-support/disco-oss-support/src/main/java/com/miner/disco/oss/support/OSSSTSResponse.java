package com.miner.disco.oss.support;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author Created by lubycoder@163.com 2018/7/10
 */
@Getter
@Setter
public class OSSSTSResponse implements Serializable {

    private static final long serialVersionUID = 4523675919913101982L;

    private String accessKeyId;
    private String accessKeySecret;
    private String securityToken;
    private String expiration;

}

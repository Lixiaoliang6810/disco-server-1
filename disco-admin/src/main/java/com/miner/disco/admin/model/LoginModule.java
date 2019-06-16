package com.miner.disco.admin.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author: chencx
 * @create: 2019-01-08
 **/
@Getter
@Setter
public class LoginModule implements Serializable {
    private Long id;

    private String loginName;

    private String nickname;

    private Integer status;
}

package com.miner.disco.admin.model.request.system;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author: chencx
 * @create: 2019-01-08
 **/
@Getter
@Setter
public class LoginRequest implements Serializable {

    private static final long serialVersionUID = 271418428639248316L;

    @NotNull(message = "用户名不能为空")
    private String username;

    @NotNull(message = "密码不能为空")
    private String password;

    private boolean rememberMe;

    private String code;
}

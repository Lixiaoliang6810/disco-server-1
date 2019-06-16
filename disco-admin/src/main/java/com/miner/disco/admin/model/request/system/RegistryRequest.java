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
public class RegistryRequest implements Serializable {

    private static final long serialVersionUID = 6017386037979783729L;
    /**
     * 用户名
     */
    @NotNull(message = "用户名不能为空")
    private String username;

    /**
     * 昵称
     */
    private String nickname;

    @NotNull(message = "密码不能为空")
    private String password;

    @NotNull(message = "手机号不能为空")
    private String mobile;

}

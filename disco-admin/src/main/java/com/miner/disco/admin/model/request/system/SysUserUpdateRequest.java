package com.miner.disco.admin.model.request.system;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author: chencx
 * @create: 2019-01-24
 **/
@Getter
@Setter
public class SysUserUpdateRequest implements Serializable {

    private static final long serialVersionUID = 6020513512914241409L;

    /**
     *
     */
    @NotNull(message = "数据错误")
    private Long id;

    /**
     * 用户名
     */
    @NotNull(message = "用户名不能为空")
    private String username;

    /**
     * 昵称
     */
    private String nickname;
}

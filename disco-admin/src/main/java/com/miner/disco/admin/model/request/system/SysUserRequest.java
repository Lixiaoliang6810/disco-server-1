package com.miner.disco.admin.model.request.system;

import com.miner.disco.basic.model.request.Pagination;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author: chencx
 * @create: 2019-01-23
 **/
@Getter
@Setter
public class SysUserRequest extends Pagination implements Serializable {

    private static final long serialVersionUID = 4196760678959251515L;

    /**
     * 昵称或者用户名
     */
    private String search;

    /**
     * 手机号
     */
    private String mobile;

    /**
     * 创建人手机号
     */
    private String createUserMobile;
}

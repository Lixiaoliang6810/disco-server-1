package com.miner.disco.admin.model.response.system;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author: chencx
 * @create: 2019-01-24
 **/
@Getter
@Setter
public class SysRoleResponse implements Serializable {

    private static final long serialVersionUID = -2983406910767655937L;

    /**
     *
     */
    private Long id;
    /**
     * 角色名称
     */
    private String name;
    /**
     * 备注
     */
    private String remark;
    /**
     * 创建时间
     */
    private java.util.Date createDate;
    /**
     *
     */
    private java.util.Date updateDate;
}

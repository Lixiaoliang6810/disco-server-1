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
public class SysRoleFormRequest implements Serializable {

    private static final long serialVersionUID = 7294540830306620752L;

    /**
     *
     */
    private Long id;
    /**
     * 角色名称
     */
    @NotNull(message = "角色名不能为空")
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

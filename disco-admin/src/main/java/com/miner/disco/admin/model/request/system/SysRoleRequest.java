package com.miner.disco.admin.model.request.system;

import com.miner.disco.basic.model.request.Pagination;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author: chencx
 * @create: 2019-01-24
 **/
@Getter
@Setter
public class SysRoleRequest extends Pagination implements Serializable {

    private static final long serialVersionUID = -3080464150539955729L;

    private String name;
}

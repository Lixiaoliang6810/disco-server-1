package com.miner.disco.admin.model.request.system;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author: chencx
 * @create: 2019-01-08
 **/
@Getter
@Setter
public class PermissionRequest implements Serializable {
    private static final long serialVersionUID = 5371492206115437066L;

    private Long moduleId;

    private String moduleCode;

    private String moduleName;

    private String group;

    private Long funcId;

    private String funcCode;

    private String funcName;
}

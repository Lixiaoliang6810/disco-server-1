package com.miner.disco.admin.model.request.system;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Set;

/**
 * @author: chencx
 * @create: 2019-01-24
 **/
@Getter
@Setter
public class RelationAuthRequest implements Serializable {

    private static final long serialVersionUID = 7322569823302777487L;

    @NotNull(message = "数据错误")
    private Long id;

    @NotNull(message = "没有关联数据")
    private Set<Long> relationIds;

    private Set<Long> defaultModuleIds;

    public void addRelationIds(Set<Long> ids){
        this.relationIds.addAll(ids);
    }
}

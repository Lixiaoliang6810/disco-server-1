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
public class RelationRequest implements Serializable {

    private static final long serialVersionUID = 8114695070097490903L;

    @NotNull(message = "数据错误")
    private Long id;

    @NotNull(message = "没有关联数据")
    private Set<Long> relationIds;
}

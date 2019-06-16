package com.miner.disco.admin.util;

import lombok.Getter;
import lombok.Setter;

/**
 * @author: chencx
 * @create: 2019-02-15
 **/
@Getter
@Setter
public class RelationModule {
    /**
     *
     */
    private Long id;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 父ID
     */
    private Long parentId;
}

package com.miner.disco.admin.model.response.system;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * @author: chencx
 * @create: 2019-02-15
 **/
@Getter
@Setter
public class MenusTreeResponse implements Serializable {

    private static final long serialVersionUID = 7363799133457243688L;
    /**
     *
     */
    private Long id;
    /**
     * 菜单名称
     */
    private String title;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 父菜单ID，一级菜单为0
     */
    private Long parentId;

    /**
     * 是否展开子节点
     */
    private boolean expand = false;

    /**
     * 是否选中
     */
    private boolean checked = false;

    /**
     * 半选中
     */
    private boolean indeterminate = false;

    /**
     * 子
     */
    private List<MenusTreeResponse> children;
}

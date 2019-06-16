package com.miner.disco.admin.model.response.system;

import com.miner.disco.basic.model.response.BaseResponse;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * @author: chencx
 * @create: 2018-07-20
 **/
@Getter
@Setter
public class UserMenuResponse extends BaseResponse implements Serializable {

    private static final long serialVersionUID = -7482337357618048238L;

    /**
     *
     */
    private Long id;
    /**
     * 菜单名称
     */
    private String menuName;
    /**
     * 路由name
     */
    private String routeName;
    /**
     * 路由path
     */
    private String routePath;
    /**
     * 外部连接href
     */
    private String href;
    /**
     * 排序
     */
    private Integer sort;
    /**
     * 菜单图标
     */
    private String icon;
    /**
     * 父菜单ID，一级菜单为0
     */
    private Long parentId;
    /**
     *
     */
    private java.util.Date createDate;
    /**
     *
     */
    private java.util.Date updateDate;

    private List<UserMenuResponse> children;

    /**
     * 角色管理菜单时用
     */
    private String title;
}

package com.miner.disco.pojo;



import lombok.*;

@Getter
@Setter
public class SysMenu {

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

}

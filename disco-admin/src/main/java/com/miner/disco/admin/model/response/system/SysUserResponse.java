package com.miner.disco.admin.model.response.system;


import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class SysUserResponse implements Serializable {

    private static final long serialVersionUID = -3834824784313803693L;

    /**
     * 
     */
    private Long id;
    /**
     * 用户名
     */
    private String username;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 手机号
     */
    private String mobile;
    /**
     * 
     */
    private Integer status;
    /**
     * 创建人手机号
     */
    private Long createUserMobile;
    /**
     * 注册时间
     */
    private java.util.Date createDate;
    /**
     * 
     */
    private java.util.Date updateDate;

}

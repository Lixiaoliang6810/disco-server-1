package com.miner.disco.pojo;



import lombok.*;

@Getter
@Setter
public class SysUser {

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
     * 密码
     */
    private String password;

    /**
     *
     */
    private String salt;

    /**
     * 手机号
     */
    private String mobile;
    /**
     * 
     */
    private Integer status;
    /**
     * 创建人id
     */
    private Long createUserId;
    /**
     * 注册时间
     */
    private java.util.Date createDate;
    /**
     * 
     */
    private java.util.Date updateDate;

}

package com.miner.disco.pojo;



import lombok.*;

@Getter
@Setter
public class SysModule {

    /**
     * 
     */
    private Long id;
    /**
     * 
     */
    private String moduleCode;
    /**
     *
     */
    private String moduleName;
    /**
     * 业务组
     */
    private String group;
    /**
     * 
     */
    private java.util.Date createDate;
    /**
     * 
     */
    private java.util.Date updateDate;

}

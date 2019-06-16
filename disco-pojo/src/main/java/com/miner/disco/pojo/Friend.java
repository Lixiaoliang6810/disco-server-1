package com.miner.disco.pojo;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @author Created by lubycoder@163.com 2019/1/3
 */
@Getter
@Setter
public class Friend {

    /**
     * 主键
     */
    private Long id;
    /**
     * 用户主键
     */
    private Long ownUserId;
    /**
     * 朋友主键
     */
    private Long himUserId;
    /**
     * 是否删除
     */
    private Integer deleted;
    /**
     * 创建时间
     */
    private Date createDate;
    /**
     * 更新时间
     */
    private Date updateDate;

}

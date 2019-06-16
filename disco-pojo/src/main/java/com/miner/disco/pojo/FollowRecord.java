package com.miner.disco.pojo;


import lombok.*;

import java.util.Date;

@Getter
@Setter
public class FollowRecord {

    /**
     * 主键
     */
    private Long id;
    /**
     * 偶像主键
     */
    private Long idolId;
    /**
     * 粉丝主键
     */
    private Long fansId;
    /**
     * 创建时间
     */
    private Date createDate;
    /**
     * 更新时间
     */
    private Date updateDate;

}

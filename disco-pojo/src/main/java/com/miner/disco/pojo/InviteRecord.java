package com.miner.disco.pojo;



import lombok.*;

import java.util.Date;

@Getter
@Setter
public class InviteRecord {

    /**
     * 主键
     */
    private Long id;
    /**
     * 邀请人主键
     */
    private Long inviterId;
    /**
     * 被邀人主键
     */
    private Long inviteeId;
    /**
     * 级别
     */
    private Integer level;
    /**
     * 创建时间
     */
    private Date createDate;
    /**
     * 更新时间
     */
    private Date updateDate;

}

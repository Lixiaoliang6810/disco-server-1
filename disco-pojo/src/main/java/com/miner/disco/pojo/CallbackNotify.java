package com.miner.disco.pojo;



import lombok.*;

import java.util.Date;

@Getter
@Setter
public class CallbackNotify {

    /**
     * 主键
     */
    private Long id;
    /**
     * 通知流水号
     */
    private String callbackSn;
    /**
     * 通知类型
     */
    private Integer callbackType;
    /**
     * 元数据
     */
    private String metadata;
    /**
     * 创建时间
     */
    private Date createDate;
    /**
     * 更新时间
     */
    private Date updateDate;

}

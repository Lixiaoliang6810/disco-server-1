package com.miner.disco.pojo;



import lombok.*;

import java.util.Date;

@Getter
@Setter
public class OrdersComplain {

    /**
     * 主键
     */
    private Long id;
    /**
     * 订单主键
     */
    private Long ordersId;
    /**
     * 投诉内容
     */
    private String content;
    /**
     * 投诉图片
     */
    private String imgsUri;
    /**
     * 投诉对象
     */
    private String object;
    /**
     * 处理状态(1 - 等待处理  2 - 处理完成)
     */
    private Integer status;
    /**
     * 创建时间
     */
    private Date createDate;
    /**
     * 更新时间
     */
    private Date updateDate;

}

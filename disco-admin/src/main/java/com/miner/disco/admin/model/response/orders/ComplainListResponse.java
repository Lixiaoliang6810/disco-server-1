package com.miner.disco.admin.model.response.orders;

import java.util.Date;
import lombok.Getter;
import lombok.Setter;

/**
 * author:linjw Date:2019/1/7 Time:15:48
 */
@Getter
@Setter
public class ComplainListResponse {

    /**
     * 主键
     */
    private Long id;
    /**
     * 订单主键
     */
    private Long ordersId;
    /**
     * 订单号
     */
    private String no;
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

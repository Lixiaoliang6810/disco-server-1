package com.miner.disco.pojo;



import lombok.*;

import java.util.Date;

@Getter
@Setter
public class Banner {

    /**
     * 主键
     */
    private Long id;
    /**
     * 图片URI
     */
    private String image;
    /**
     * 链接地址
     */
    private String link;
    /**
     * 排序序号
     */
    private Integer sort;
    /**
     * 开始时间
     */
    private Date startTime;
    /**
     * 结束时间
     */
    private Date endTime;
    /**
     * 是否删除(1 - 正常 2 - 删除)
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

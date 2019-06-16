package com.miner.disco.pojo;



import lombok.*;

import java.util.Date;

@Getter
@Setter
public class Dynamic {

    /**
     * 主键
     */
    private Long id;
    /**
     * 用户主键
     */
    private Long userId;
    /**
     * 动态内容
     */
    private String content;
    /**
     * 动态图片
     */
    private String images;
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

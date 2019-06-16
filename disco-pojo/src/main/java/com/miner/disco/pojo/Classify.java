package com.miner.disco.pojo;



import lombok.*;

import java.util.Date;

@Getter
@Setter
public class Classify {

    /**
     * 主键
     */
    private Long id;
    /**
     * 分类类型
     */
    private String type;
    /**
     * 分类名称
     */
    private String name;
    /**
     * 是否删除(1 - 正常 2 - 删除)
     */
    private String deleted;
    /**
     * 创建时间
     */
    private Date createDate;
    /**
     * 更新时间
     */
    private Date updateDate;

}

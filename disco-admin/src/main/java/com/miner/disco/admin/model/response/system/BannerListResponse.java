package com.miner.disco.admin.model.response.system;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * author:linjw Date:2019/1/7 Time:19:09
 */
@Getter
@Setter
public class BannerListResponse implements Serializable {

    private static final long serialVersionUID = -3164942350078480131L;

    /**
     * 主键
     */
    private Long id;

    /**
     * 图片
     */
    private String image;

    /**
     * 图片link
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
     * 创建时间
     */
    private Date createDate;
    /**
     * 更新时间
     */
    private Date updateDate;
}

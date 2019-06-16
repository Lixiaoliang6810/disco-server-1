package com.miner.disco.pojo;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @ClassName Report
 * @Auther: wz1016_vip@163.com
 * @Date: 2019/6/16 17:00
 * @Description: TODO
 */
@Getter
@Setter
public class Report {


    private Long id;
    /**
     * 举报人id
     *
     */
    private Long reporterId;
    /**
     * 被举报人id
     *
     */
    private Long reportedId;
    /**
     * 举报内容
     *
     */
    private String content;
    /**
     * 举报截图
     *
     */
    private String images;

    /**
     * 创建时间
     */
    private Date createDate;

    public Report(){
        super();
        this.createDate = new Date();
    }
}

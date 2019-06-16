package com.miner.disco.front.model.response;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @ClassName ReportResponse
 * @Auther: wz1016_vip@163.com
 * @Date: 2019/6/16 17:01
 * @Description: TODO
 */
@Data
public class ReportResponse implements Serializable {
    private static final long serialVersionUID = 1539984078590579190L;
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
}

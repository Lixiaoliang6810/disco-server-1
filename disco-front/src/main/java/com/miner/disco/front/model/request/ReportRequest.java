package com.miner.disco.front.model.request;

import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName ReportRequest
 * @Auther: wz1016_vip@163.com
 * @Date: 2019/6/16 14:59
 * @Description: TODO
 */
@Data
public class ReportRequest implements Serializable {
    private static final long serialVersionUID = -7767836691424005965L;

//    /**
//     * 举报人id
//     *
//     */
//    private Long reporterId;
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

}

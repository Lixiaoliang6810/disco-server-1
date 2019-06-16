package com.miner.disco.admin.model.response.system;

import java.util.Date;
import lombok.Getter;
import lombok.Setter;

/**
 * author:linjw Date:2019/2/19 Time:17:07
 */
@Getter
@Setter
public class FeedbackListResponse {

    /**
     * 用户主键
     */
    private Long userId;
    /**
     * 用户昵称
     */
    private String username;
    /**
     * 用户手机号
     */
    private String mobile;
    /**
     * 反馈内容
     */
    private String content;
    /**
     * 创建时间
     */
    private Date createDate;
}

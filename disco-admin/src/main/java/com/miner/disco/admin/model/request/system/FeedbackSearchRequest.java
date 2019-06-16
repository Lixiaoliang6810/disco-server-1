package com.miner.disco.admin.model.request.system;

import com.miner.disco.basic.model.request.Pagination;
import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

/**
 * author:linjw Date:2019/2/19 Time:17:10
 */
@Getter
@Setter
public class FeedbackSearchRequest extends Pagination implements Serializable {

    /**
     * 用户昵称
     */
    private String username;
    /**
     * 反馈内容
     */
    private String content;
    /**
     * 用户手机号
     */
    private String mobile;
}

package com.miner.disco.admin.model.response.member;

import java.util.Date;
import lombok.Getter;
import lombok.Setter;

/**
 * author:linjw Date:2019/1/7 Time:17:44
 */
@Getter
@Setter
public class FollowRecordListResponse {

    /**
     * 用户主键
     */
    private Long userId;
    /**
     * 用户昵称
     */
    private String nickname;
    /**
     * 关注时间
     */
    private Date createDate;
}

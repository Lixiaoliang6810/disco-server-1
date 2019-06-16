package com.miner.disco.admin.model.response.member;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * author:linjw Date:2019/1/8 Time:10:04
 */
@Getter
@Setter
public class MemberDynamicListResponse {

    /**
     * 主键
     */
    private Long id;
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

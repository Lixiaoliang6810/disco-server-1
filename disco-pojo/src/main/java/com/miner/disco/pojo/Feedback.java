package com.miner.disco.pojo;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @author Created by lubycoder@163.com 2019-2-14
 */
@Getter
@Setter
public class Feedback {

    /**
     * 主键
     */
    private Long id;
    /**
     * 用户主键
     */
    private Long userId;
    /**
     * 反馈内容
     */
    private String content;
    /**
     * 创建时间
     */
    private Date createDate;
    /**
     * 更新时间
     */
    private Date updateDate;

}

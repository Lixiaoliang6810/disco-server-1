package com.miner.disco.front.model.response;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Created by lubycoder@163.com 2018/12/29
 */
@Getter
@Setter
public class MerchantEvaluateListResponse implements Serializable {

    private static final long serialVersionUID = -1841534333737992852L;

    /**
     * 用户主键
     */
    private Long userId;
    /**
     * 昵称
     */
    private String nickname;
    /**
     * 头像
     */
    private String avatar;
    /**
     * 分数
     */
    private Float score;
    /**
     * 评论内容
     */
    private String content;
    /**
     * 评论图片
     */
    private String images;
    /**
     * 评论时间
     */
    private Date createDate;

}

package com.miner.disco.front.model.response;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Created by lubycoder@163.com 2018/12/24
 */
@Getter
@Setter
@EqualsAndHashCode
public class DynamicsListResponse implements Serializable {

    private static final long serialVersionUID = -5260315560270882659L;
    /**
     * 动态ID
     */
    private Long dynamicId;
    /**
     * 用户ID
     */
    private Long userId;
    /**
     * 头像
     */
    private String avatar;
    /**
     * 昵称
     */
    private String nickname;
    /**
     * 内容
     */
    private String content;
    /**
     * 动态图片
     */
    private String images;
    /**
     * 创建时间
     */
    private Date createDate;

}

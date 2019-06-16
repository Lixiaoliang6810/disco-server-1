package com.miner.disco.front.model.response;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author Created by lubycoder@163.com 2018/12/24
 */
@Getter
@Setter
public class MembersResponse implements Serializable {

    private static final long serialVersionUID = 486800019953922072L;

    /**
     * 主键
     */
    private Long id;
    /**
     * 昵称
     */
    private String nickname;
    /**
     * 头像
     */
    private String avatar;
    /**
     * 性别（1 - 男 2 - 女）
     */
    private Integer gender;
    /**
     * 标签(多个标签使用逗号拆分)
     */
    private String label;

}

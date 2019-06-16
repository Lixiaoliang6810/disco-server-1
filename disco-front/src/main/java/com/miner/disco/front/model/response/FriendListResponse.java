package com.miner.disco.front.model.response;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author Created by lubycoder@163.com 2019/1/3
 */
@Getter
@Setter
public class FriendListResponse implements Serializable {

    private static final long serialVersionUID = 330199595071291247L;

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
     * 标语
     */
    private String slogan;
    /**
     * IM账号
     */
    private String imAccount;

}

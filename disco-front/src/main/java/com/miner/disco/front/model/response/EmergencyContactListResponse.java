package com.miner.disco.front.model.response;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author Created by lubycoder@163.com 2018/12/25
 */
@Getter
@Setter
public class EmergencyContactListResponse implements Serializable {

    private static final long serialVersionUID = 2347504499348907245L;

    /**
     * 主键
     */
    private Long id;
    /**
     * 姓名
     */
    private String realname;
    /**
     * 手机号
     */
    private String mobile;

}

package com.miner.disco.front.model.response;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Created by lubycoder@163.com 2019/1/7
 */
@Getter
@Setter
public class MemberTaCenterResponse implements Serializable {

    private static final long serialVersionUID = -315506017400500288L;

    private Long id;
    private String avatar;
    private String nickname;
    private Integer gender;
    private String city;
    private String label;
    private String slogan;
    private Integer weight;
    private Integer height;
    private Date birthday;
    private String backgroundImage;
    /**
     * {@link com.miner.disco.basic.constants.BooleanStatus}
     * 是否是好友(1 - 是 2 - 否)
     */
    private Integer friended;
    private String imAccount;

}

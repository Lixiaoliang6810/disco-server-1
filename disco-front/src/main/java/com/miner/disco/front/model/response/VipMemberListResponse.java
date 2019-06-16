package com.miner.disco.front.model.response;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Created by lubycoder@163.com 2019/1/8
 */
@Getter
@Setter
public class VipMemberListResponse implements Serializable {

    private static final long serialVersionUID = -8708129986501627228L;

    private Long userId;
    private String label;
    private Integer gender;
    private String avatar;
    private String nickname;
    private Double longitude;
    private Double latitude;
    private Integer recommend;
    private Double distance;
    private Date createDate;
    private Date updateDate;
    private String backgroundImage;

}

package com.miner.disco.front.model.response;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Created by lubycoder@163.com 2019-3-6
 */
@Getter
@Setter
public class ApplyAssembleOrdersListResponse implements Serializable {

    private static final long serialVersionUID = 861658442361973745L;

    private Long ordersId;

    private String merchantName;

    private Integer status;

    private String logo;

    private String address;

    private Date arrivalTime;

    private Integer launchUserId;

    private String launchUserAvatar;

}

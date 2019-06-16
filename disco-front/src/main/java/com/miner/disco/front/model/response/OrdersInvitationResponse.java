package com.miner.disco.front.model.response;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author Created by lubycoder@163.com 2019-3-6
 */
@Getter
@Setter
public class OrdersInvitationResponse implements Serializable {

    private static final long serialVersionUID = -805800096272279047L;

    private Long ordersInvitationId;

    private Long userId;

    private String nickname;

    private String avatar;

    private Integer owner;

    private Integer status;

}

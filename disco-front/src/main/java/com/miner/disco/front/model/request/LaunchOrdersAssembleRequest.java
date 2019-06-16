package com.miner.disco.front.model.request;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author Created by lubycoder@163.com 2019-3-6
 */
@Getter
@Setter
public class LaunchOrdersAssembleRequest implements Serializable {

    private static final long serialVersionUID = -1260328928014951319L;

    private Long userId;

    private Long ordersId;

    private Integer assembleSeatsCount;

}

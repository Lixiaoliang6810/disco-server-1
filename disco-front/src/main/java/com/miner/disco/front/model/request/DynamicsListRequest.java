package com.miner.disco.front.model.request;

import com.miner.disco.basic.model.request.Pagination;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Created by lubycoder@163.com 2019/1/3
 */
@Getter
@Setter
public class DynamicsListRequest extends Pagination {

    private static final long serialVersionUID = -8076528264328412910L;

    private Long userId;

}

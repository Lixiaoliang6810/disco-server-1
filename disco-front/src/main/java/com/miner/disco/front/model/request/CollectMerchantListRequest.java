package com.miner.disco.front.model.request;

import com.miner.disco.basic.model.request.Pagination;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Created by lubycoder@163.com 2019/1/7
 */
@Getter
@Setter
public class CollectMerchantListRequest extends Pagination {

    private static final long serialVersionUID = -8530604367700725122L;

    private Long userId;
}

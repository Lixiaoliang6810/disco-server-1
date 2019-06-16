package com.miner.disco.mch.model.request;

import com.miner.disco.basic.model.request.Pagination;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author Created by lubycoder@163.com 2019-2-21
 */
@Getter
@Setter
public class MerchantSettlementRecordRequest extends Pagination implements Serializable {

    private static final long serialVersionUID = -7005135620499624029L;

    private Long merchantId;

}

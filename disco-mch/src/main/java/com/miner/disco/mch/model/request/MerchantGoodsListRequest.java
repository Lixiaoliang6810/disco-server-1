package com.miner.disco.mch.model.request;

import com.miner.disco.basic.model.request.Pagination;
import com.miner.disco.pojo.MerchantGoods;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author Created by lubycoder@163.com 2019/1/9
 */
@Getter
@Setter
public class MerchantGoodsListRequest extends Pagination implements Serializable {

    private static final long serialVersionUID = 5108480426556040723L;

    private Long merchantId;

    private Integer status = MerchantGoods.STATUS.UPPER.getKey();

}

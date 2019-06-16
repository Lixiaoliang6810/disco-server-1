package com.miner.disco.front.model.response;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author Created by lubycoder@163.com 2019/1/7
 */
@Getter
@Setter
public class CollectMerchantListResponse implements Serializable {

    private static final long serialVersionUID = -8417595207549272876L;

    private Long merchantId;
    private String name;
    private Float score;
    private String address;
    private String logo;

}

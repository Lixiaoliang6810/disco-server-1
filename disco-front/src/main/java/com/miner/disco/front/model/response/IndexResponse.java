package com.miner.disco.front.model.response;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * @author Created by lubycoder@163.com 2019/1/7
 */
@Getter
@Setter
public class IndexResponse implements Serializable {

    private static final long serialVersionUID = 3679809652649759790L;

    private List<BannerListResponse> banner;

    private List<ClassifyListResponse> classify;

    private List<MerchantListResponse> merchant;

}

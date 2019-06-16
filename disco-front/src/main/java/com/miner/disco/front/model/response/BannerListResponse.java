package com.miner.disco.front.model.response;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author Created by lubycoder@163.com 2019/1/7
 */
@Getter
@Setter
public class BannerListResponse implements Serializable {

    private static final long serialVersionUID = -5979773206254550613L;

    private String image;
    private String link;

}

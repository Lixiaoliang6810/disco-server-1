package com.miner.disco.basic.model.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by linjw on 2018/7/20.
 */
@Getter
@Setter
@Builder
public class PageResponse<T> {

    private Integer total;

    private T list;

    public PageResponse(Integer total, T list) {
        this.total = total;
        this.list = list;
    }
}

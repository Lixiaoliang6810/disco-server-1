package com.miner.disco.front.model.request;

import com.miner.disco.basic.model.request.Pagination;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author Created by lubycoder@163.com 2019/1/7
 */
@Getter
@Setter
public class OrdersListRequest extends Pagination implements Serializable {

    private static final long serialVersionUID = -3672117089192837540L;

    private Long userId;

    private QUERY_STATUS status = QUERY_STATUS.ALL;

    public enum QUERY_STATUS {
        ALL(null),
        PENDING("1"),
        USABLE("2"),
        REFUND("5");

        private String key;

        QUERY_STATUS(String key) {
            this.key = key;
        }

        public String getKey() {
            return key;
        }
    }

}

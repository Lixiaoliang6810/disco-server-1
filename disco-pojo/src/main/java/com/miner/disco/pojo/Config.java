package com.miner.disco.pojo;


import com.miner.disco.basic.constants.BasicEnum;
import lombok.*;

import java.util.Date;

@Getter
@Setter
public class Config {

    /**
     * 主键
     */
    private Long id;
    /**
     * KEY
     */
    private String key;
    /**
     * VALUE
     */
    private String value;
    /**
     * 备注
     */
    private String remark;
    /**
     * 创建时间
     */
    private Date createDate;
    /**
     * 更新时间
     */
    private Date updateDate;

    public enum KEY implements BasicEnum {
        
        VIP_GUIDE_ROYALTY(1, "VIP引导员抽成");

        Integer key;
        String value;

        @Override
        public Integer getKey() {
            return this.key;
        }

        @Override
        public String getValue() {
            return this.value;
        }

        KEY(Integer key, String value) {
            this.key = key;
            this.value = value;
        }

    }

}

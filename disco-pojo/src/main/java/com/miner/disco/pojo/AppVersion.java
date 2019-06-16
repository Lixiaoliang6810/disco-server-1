package com.miner.disco.pojo;

import com.miner.disco.basic.constants.BasicEnum;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @author Created by lubycoder@163.com 2019-2-14
 */
@Getter
@Setter
public class AppVersion {

    /**
     * 主键
     */
    private Long id;
    /**
     * APP类型
     * {@link com.miner.disco.basic.constants.AppEnum}
     */
    private String app;
    /**
     * 文件
     */
    private String file;
    /**
     * 类型
     */
    private Integer type;
    /**
     * 版本号
     */
    private String version;
    /**
     * 强制更新
     */
    private Integer compulsion;
    /**
     * 创建时间
     */
    private Date createDate;
    /**
     * 更新时间
     */
    private Date updateDate;

    public enum TYPE implements BasicEnum {
        IOS(1, "iOS"),
        AOS(2, "Android");

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

        TYPE(Integer key, String value) {
            this.key = key;
            this.value = value;
        }
    }


}

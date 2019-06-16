package com.miner.disco.mch.model.response;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author Created by lubycoder@163.com 2019-2-19
 */
@Getter
@Setter
public class ClassifySelectorResponse implements Serializable {

    private static final long serialVersionUID = -1112871103327704526L;

    /**
     * 主键
     */
    private Long id;
    /**
     * 分类名称
     */
    private String name;

}

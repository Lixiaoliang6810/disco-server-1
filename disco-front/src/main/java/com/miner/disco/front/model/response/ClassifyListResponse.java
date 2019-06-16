package com.miner.disco.front.model.response;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author Created by lubycoder@163.com 2019/1/3
 */
@Getter
@Setter
public class ClassifyListResponse implements Serializable {

    private static final long serialVersionUID = 8657002533367958275L;
    /**
     * 主键
     */
    private Long id;
    /**
     * 分类名称
     */
    private String name;
}

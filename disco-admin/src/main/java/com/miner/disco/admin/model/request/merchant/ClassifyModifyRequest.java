package com.miner.disco.admin.model.request.merchant;

import lombok.Getter;
import lombok.Setter;

/**
 * author:linjw Date:2019/1/3 Time:16:07
 */
@Getter
@Setter
public class ClassifyModifyRequest {

    /**
     * 主键
     */
    private Long id;
    /**
     * 分类类型
     */
    private String type;
    /**
     * 分类名称
     */
    private String name;
    /**
     * 是否删除(1 - 正常 2 - 删除)
     */
    private String deleted;
}

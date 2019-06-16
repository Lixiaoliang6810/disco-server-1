package com.miner.disco.admin.auth.model;

import com.google.common.collect.Lists;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * @author: chencx
 * @create: 2019-01-24
 **/
@Getter
@Setter
public class GroupBean implements Serializable {

    private static final long serialVersionUID = 4973024324190967717L;

    private String name;

    private List<ModuleBean> moduleBeans;

    /**
     * 新增模块
     * @param moduleBeans
     */
    public void addModuleBean (List<ModuleBean> moduleBeans){
        if (this.moduleBeans == null){
            this.moduleBeans = Lists.newArrayList();
        }
        this.moduleBeans.addAll(moduleBeans);
    }
}

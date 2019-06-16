package com.miner.disco.admin.dao.system;

import com.miner.disco.admin.auth.model.FunctionBean;
import com.miner.disco.admin.auth.model.ModuleBean;
import com.miner.disco.admin.auth.model.RelationBean;
import org.apache.ibatis.annotations.Param;

import java.util.Set;

/**
 * @author: chencx
 * @create: 2019-01-07
 **/
public interface ScanDao {

    /**
     * 查询所有模块
     * @return
     */
    Set<ModuleBean> queryAllModules();

    /**
     * 查询所有方法
     * @return
     */
    Set<FunctionBean> queryAllFunc();

    /**
     * 批量新增模块
     * @param moduleBeans
     */
    void inserModules(Set<ModuleBean> moduleBeans);

    /**
     * 批量新增方法
     * @param functionBeans
     */
    void inserFuncs(Set<FunctionBean> functionBeans);

    /**
     * 批量更新模块
     * @param moduleBeans
     */
    void updateModules(Set<ModuleBean> moduleBeans);

    /**
     * 批量更新方法
     * @param functionBeans
     */
    void updateFuncs(Set<FunctionBean> functionBeans);

    /**
     * 批量删除模块关联方法
     * @param moduleBeans
     */
    void deleteRelationsByModules(@Param("moduleBeans") Set<ModuleBean> moduleBeans);

    /**
     * 批量删除方法关联模块
     * @param functionBeans
     */
    void deleteRelationsByFuncs(@Param("functionBeans") Set<FunctionBean> functionBeans);

    /**
     * 批量新增模块关联方法
     * @param relationBeans
     */
    void insertRelations(Set<RelationBean> relationBeans);

    /**
     * 批量删除模块
     * @param moduleBeans
     */
    void deleteModules(@Param("moduleBeans") Set<ModuleBean> moduleBeans);

    /**
     * 批量删除方法
     * @param functionBeans
     */
    void deleteFuncs(@Param("functionBeans") Set<FunctionBean> functionBeans);

}

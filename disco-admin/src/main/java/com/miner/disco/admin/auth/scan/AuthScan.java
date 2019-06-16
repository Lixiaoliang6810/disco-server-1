package com.miner.disco.admin.auth.scan;
import com.google.common.collect.Sets;
import com.miner.disco.admin.auth.BasicFunction;
import com.miner.disco.admin.auth.BasicModule;
import com.miner.disco.admin.auth.DefaultFunc;
import com.miner.disco.admin.auth.model.FunctionBean;
import com.miner.disco.admin.auth.model.ModuleBean;
import com.miner.disco.admin.auth.model.RelationBean;
import com.miner.disco.admin.dao.system.ScanDao;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.reflections.Reflections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author: chencx
 * @create: 2019-01-07
 **/
@Slf4j
@Component
public class AuthScan  extends AuthAbstractScan{

    @Autowired
    private ScanDao scanDao;

    @Override
    @Transactional(readOnly = false, rollbackFor = RuntimeException.class)
    public void scan() {
        Reflections reflections = new Reflections(authScan);
        Set<Class<? extends BasicModule>> moduleClzs = reflections.getSubTypesOf(BasicModule.class); //scan module class
        Set<Class<? extends BasicFunction>> funcClzs = reflections.getSubTypesOf(BasicFunction.class);//scan function class
        funcClzs.add(DefaultFunc.class);//add default function

        Set<FunctionBean> currentFuncBeans = scanDao.queryAllFunc();//original function
        Set<ModuleBean> currentModuleBeans = scanDao.queryAllModules();//original module

        Set<FunctionBean> scanFuncBeans = null;  //to insert func
        Set<ModuleBean> scanModuleBeans = null; //to insert module
        try {
            scanModuleBeans = getModules(moduleClzs);
            scanFuncBeans = getFuncs(funcClzs);
        } catch (Exception e) {
            log.error("scan auth init error. {}", e);
            return;
        }

        Set<FunctionBean> scanFuncAddeds = null;
        Set<FunctionBean> scanFuncDels = Sets.newHashSet();
        if (CollectionUtils.isNotEmpty(currentFuncBeans)){ //
            Set<String> scanFuncCode = scanFuncBeans.stream().map(FunctionBean::getFuncCode).collect(Collectors.toSet());
            Set<String> currentFuncCodes = currentFuncBeans.stream().map(FunctionBean::getFuncCode).collect(Collectors.toSet());
            scanFuncCode.removeAll(currentFuncCodes);//scan-code and to current-code difference set (added function)
            scanFuncAddeds = scanFuncBeans.stream().filter(functionBean -> scanFuncCode.contains(functionBean.getFuncCode())).collect(Collectors.toSet());

            currentFuncsFor: for (FunctionBean currentFuncBean : currentFuncBeans ){
                for (FunctionBean scanFuncBean : scanFuncBeans){
                    if (StringUtils.equals(currentFuncBean.getFuncCode(), scanFuncBean.getFuncCode())){
                        currentFuncBean.setFuncName(scanFuncBean.getFuncName());
                        continue currentFuncsFor;
                    }
                }
                scanFuncDels.add(currentFuncBean);
            }
        }else{
            scanFuncAddeds = Sets.newHashSetWithExpectedSize(scanFuncBeans.size());
            scanFuncAddeds.addAll(scanFuncBeans);
        }

        if (CollectionUtils.isNotEmpty(scanFuncAddeds)){
            scanDao.inserFuncs(scanFuncAddeds);//insert func
        }

        //merge insert func and update func (all func)
        Set<FunctionBean> allFuncs = Sets.newHashSet();
        allFuncs.addAll(scanFuncAddeds);

        if (CollectionUtils.isNotEmpty(currentFuncBeans)){
            scanDao.updateFuncs(currentFuncBeans);//update func
            allFuncs.addAll(currentFuncBeans);
        }

        Set<ModuleBean> scanModuleAddeds = null;
        Set<ModuleBean> scanModuleDels = null;
        if (CollectionUtils.isNotEmpty(currentModuleBeans)){
            Set<String> scanModuleCode = scanModuleBeans.stream().map(ModuleBean::getModuleCode).collect(Collectors.toSet());
            Set<String> currentModuleCodes = currentModuleBeans.stream().map(ModuleBean::getModuleCode).collect(Collectors.toSet());
            scanModuleCode.removeAll(currentModuleCodes);
            scanModuleAddeds = scanModuleBeans.stream().filter(moduleBean -> scanModuleCode.contains(moduleBean.getModuleCode())).collect(Collectors.toSet());

            currentModulesFor: for (ModuleBean currentModuleBean : currentModuleBeans ){
                for (ModuleBean scanModuleBean : scanModuleBeans){
                    if (StringUtils.equals(currentModuleBean.getModuleCode(), scanModuleBean.getModuleCode())){
                        currentModuleBean.setModuleName(scanModuleBean.getModuleName());
                        currentModuleBean.setGroup(scanModuleBean.getGroup());
                        continue currentModulesFor;
                    }
                }
                scanModuleDels.add(currentModuleBean);
            }
        }else{
            scanModuleAddeds = Sets.newHashSetWithExpectedSize(scanModuleBeans.size());
            scanModuleAddeds.addAll(scanModuleBeans);
        }

        if (CollectionUtils.isNotEmpty(scanModuleAddeds)){
            scanDao.inserModules(scanModuleAddeds); //insert module
        }
        if (CollectionUtils.isNotEmpty(currentModuleBeans)){
            scanDao.updateModules(currentModuleBeans); //update module
        }

       if (CollectionUtils.isNotEmpty(scanModuleDels)){
           scanDao.deleteModules(scanModuleDels);scanDao.deleteRelationsByModules(scanModuleDels);//del module and module relation
       }

       if (CollectionUtils.isNotEmpty(scanFuncDels)){
           scanDao.deleteFuncs(scanFuncDels);scanDao.deleteRelationsByFuncs(scanFuncDels);////del func and func relation
       }

        //module Relation func
        //scanFuncAddeds relation currentModuleBeans. (repair original relation)
        Set<RelationBean> relations = Sets.newHashSet();
        if (CollectionUtils.isNotEmpty(currentModuleBeans)){
            for (FunctionBean scanFuncAdded : scanFuncAddeds){
                currentModuleBeans.forEach(currentModuleBean -> {
                    relations.add(new RelationBean(currentModuleBean, scanFuncAdded));
                });
            }
        }
        //scanModuleAddeds relation allFuncs. (insert relation)
        for (ModuleBean scanModuleAdded : scanModuleAddeds){
            allFuncs.forEach(allFunc -> {
                relations.add(new RelationBean(scanModuleAdded, allFunc));
            });
        }

        scanDao.insertRelations(relations);   //insert relations

    }


}

package com.miner.disco.admin.auth.scan;
import com.google.common.collect.Sets;
import com.miner.disco.admin.auth.BasicFunction;
import com.miner.disco.admin.auth.BasicModule;
import com.miner.disco.admin.auth.model.FunctionBean;
import com.miner.disco.admin.auth.model.ModuleBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import java.util.Set;

/**
 * @author: chencx
 * @create: 2019-01-07
 **/
@Slf4j
public abstract class AuthAbstractScan {

    @Value("${auth.scan}")
    protected String authScan;

    public abstract void scan();

    protected Set<ModuleBean> getModules(Set<Class<? extends BasicModule>> moduleClzs) throws IllegalAccessException, InstantiationException {
        Set<ModuleBean> moduleBeans = Sets.newHashSet();
        for (Class<? extends BasicModule> c : moduleClzs){
            BasicModule basicModule = c.newInstance();
            ModuleBean moduleBean = new ModuleBean();
            moduleBean.setModuleCode(basicModule.getCode());
            moduleBean.setModuleName(basicModule.getName());
            moduleBean.setGroup(basicModule.group());
            moduleBeans.add(moduleBean);
        }
        return moduleBeans;
    }

    protected Set<FunctionBean> getFuncs(Set<Class<? extends BasicFunction>> funcClzs) throws IllegalAccessException, InstantiationException {
        Set<FunctionBean> modelBeans = Sets.newHashSet();
        for (Class<? extends BasicFunction> c : funcClzs){
            BasicFunction basicModule = c.newInstance();
            FunctionBean functionBean = new FunctionBean();
            functionBean.setFuncCode(basicModule.getCode());
            functionBean.setFuncName(basicModule.getName());
            modelBeans.add(functionBean);
        }
        return modelBeans;
    }

    protected Set<ModuleBean> getModules(Set<Class<? extends BasicModule>> moduleClzs, Set<Class<? extends BasicFunction>> funcClzs) throws InstantiationException, IllegalAccessException {
        Set<FunctionBean> functionBeans = getFuncs(funcClzs);
        Set<ModuleBean> moduleBeans = Sets.newHashSet();
        for (Class<? extends BasicModule> c : moduleClzs){
            BasicModule basicModule = c.newInstance();
            ModuleBean moduleBean = new ModuleBean();
            moduleBean.setModuleCode(basicModule.getCode());
            moduleBean.setModuleCode(basicModule.getName());
            moduleBean.setGroup(basicModule.group());
            moduleBean.addFunctions(functionBeans);
            moduleBeans.add(moduleBean);
        }
        return moduleBeans;
    }

}

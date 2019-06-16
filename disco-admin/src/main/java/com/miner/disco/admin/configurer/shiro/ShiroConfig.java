package com.miner.disco.admin.configurer.shiro;

import com.miner.disco.admin.configurer.component.ShiroRealm;
import com.miner.disco.admin.configurer.shiro.filter.CORSAuthenticationFilter;
import com.miner.disco.admin.constant.RestConst;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.crazycake.shiro.RedisCacheManager;
import org.crazycake.shiro.RedisManager;
import org.crazycake.shiro.RedisSessionDAO;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import javax.servlet.Filter;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author: chencx
 * @create: 2018-11-19
 **/
@Configuration
public class ShiroConfig {

   /* @Value("${spring.redis.host}")
    private String host;
    @Value("${spring.redis.port}")
    private int port;
    @Value("${spring.redis.timeout}")
    private int timeout;
    @Value("${spring.redis.password}")
    private String password;
    @Value("${spring.redis.database}")
    private int database = 0;*/

   @Autowired
   private RedisProperties redisProperties;



        @Bean
        public ShiroFilterFactoryBean shirFilter(SecurityManager securityManager) {
            System.out.println("ShiroConfiguration.shirFilter()");
            ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
            shiroFilterFactoryBean.setSecurityManager(securityManager);

            Map<String, String> filterChainDefinitionMap = new LinkedHashMap<String, String>();
            //注意过滤器配置顺序 不能颠倒
            //配置退出 过滤器,其中的具体的退出代码Shiro已经替我们实现了，登出后跳转配置的loginUrl
            filterChainDefinitionMap.put("/logout", "logout");
            // 配置不会被拦截的链接 顺序判断
            filterChainDefinitionMap.put("/static/**", "anon");
            filterChainDefinitionMap.put("/login", "anon");
            filterChainDefinitionMap.put("/**", "authc");
            shiroFilterFactoryBean.setLoginUrl("/unauth");
            //配置shiro默认登录界面地址，前后端分离中登录界面跳转应由前端路由控制，后台仅返回json数据
            shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);

            filterChainDefinitionMap.put("/**", "corsAuthenticationFilter");
            Map<String, Filter> filterMap = new LinkedHashMap<>();
            filterMap.put("corsAuthenticationFilter", corsAuthenticationFilter());
            shiroFilterFactoryBean.setFilters(filterMap);

            return shiroFilterFactoryBean;
        }

        public CORSAuthenticationFilter corsAuthenticationFilter(){
            return new CORSAuthenticationFilter();
        }

        /**
         * 凭证匹配器
         * （由于我们的密码校验交给Shiro的SimpleAuthenticationInfo进行处理了）
         * @return
         */
        @Bean
        public HashedCredentialsMatcher hashedCredentialsMatcher() {
            HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
            hashedCredentialsMatcher.setHashAlgorithmName("md5");//散列算法:这里使用MD5算法;
            hashedCredentialsMatcher.setHashIterations(RestConst.MD5_HASH_ITERATIONS);//散列的次数，比如散列两次，相当于 md5(md5(""));
            return hashedCredentialsMatcher;
        }

        @Bean
        public ShiroRealm shiroRealm() {
            ShiroRealm shiroRealm = new ShiroRealm();
            shiroRealm.setCredentialsMatcher(hashedCredentialsMatcher());
            return shiroRealm;
        }


        @Bean
        public DefaultWebSecurityManager securityManager() {
            DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
            securityManager.setRealm(shiroRealm());
            // 自定义session管理 使用redis
            securityManager.setSessionManager(sessionManager());
            // 自定义缓存实现 使用redis
            securityManager.setCacheManager(cacheManager());
            return securityManager;
        }

        //自定义sessionManager
        @Bean
        public SessionManager sessionManager() {
            SimpleCookie simpleCookie = new SimpleCookie(RestConst.AUTHORIZATION);
//            simpleCookie.setPath("/");
            simpleCookie.setHttpOnly(false);
            SessionManager sessionManager = new SessionManager();
            sessionManager.setSessionDAO(redisSessionDAO());
            sessionManager.setSessionIdCookieEnabled(false);
            sessionManager.setSessionIdUrlRewritingEnabled(false);
            sessionManager.setDeleteInvalidSessions(true);
            sessionManager.setSessionIdCookie(simpleCookie);
            return sessionManager;
        }

    /**
     * cacheManager 缓存 redis实现
     * @return
     */
    public RedisCacheManager cacheManager() {
        RedisCacheManager redisCacheManager = new RedisCacheManager();
        redisCacheManager.setRedisManager(redisManager());
        redisCacheManager.setKeyPrefix(RestConst.REDIS_CACHE_KEY);
        return redisCacheManager;
    }

    /**
     * RedisSessionDAO shiro sessionDao层的实现 通过redis
     */
    @Bean
    public RedisSessionDAO redisSessionDAO() {
        RedisSessionDAO redisSessionDAO = new RedisSessionDAO();
        redisSessionDAO.setRedisManager(redisManager());
        redisSessionDAO.setKeyPrefix(RestConst.REDIS_CACHE_KEY);
        return redisSessionDAO;
    }

    /**
     * 配置shiro redisManager
     * @return
     */
    @Bean
    public RedisManager redisManager() {
        RedisManager redisManager = new RedisManager();
        /*redisManager.setHost(this.host + ":" + this.port);
        redisManager.setPassword(this.password);
        redisManager.setDatabase(this.database);*/
        redisManager.setHost(redisProperties.getHost() + ":" + redisProperties.getPort());
        redisManager.setPassword(redisProperties.getPassword());
        redisManager.setDatabase(redisProperties.getDatabase());
        redisManager.setTimeout(1800); //设置过期时间
        return redisManager;
    }

    /**
     * 限制同一账号登录同时登录人数控制
     * @return
     */
/*    @Bean
    public SessionControlFilter kickoutSessionControlFilter() {
        SessionControlFilter kickoutSessionControlFilter = new SessionControlFilter();
        kickoutSessionControlFilter.setCache(cacheManager());
        kickoutSessionControlFilter.setSessionManager(sessionManager());
        kickoutSessionControlFilter.setKickoutAfter(false);
        kickoutSessionControlFilter.setMaxSession(1);
        kickoutSessionControlFilter.setKickoutUrl("/kickout");
        return kickoutSessionControlFilter;
    }*/

    /**
     * 开启shiro aop注解支持.
     * 使用代理方式;所以需要开启代码支持;
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(){
        AuthorizationAttributeSourceAdvisor advisor = new ShiroAdvisor();
        advisor.setSecurityManager(securityManager());
        return advisor;
    }

    @Bean
    @DependsOn({"lifecycleBeanPostProcessor"})
    public DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator(){
        DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        advisorAutoProxyCreator.setProxyTargetClass(true);
        return advisorAutoProxyCreator;
    }

    /**
     * 使用@Configuration配置，会在上下文初始化的时候强制的注入一些依赖。导致一下不可知的初始化。尤其是创建BeanPostProcessor 和BeanFactoryPostProcessor的时候（LifecycleBeanPostProcessor正是BeanPostProcessor 的子类）。应该讲这些创建Bean的方法前面加上static。让使用configuration的类在没有实例化的时候不会去过早的要求@Autowired和@Value进行注入。
     * @return
     */
    @Bean
    public static LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }
}

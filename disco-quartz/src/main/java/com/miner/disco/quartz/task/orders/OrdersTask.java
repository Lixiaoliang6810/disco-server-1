package com.miner.disco.quartz.task.orders;

import com.miner.disco.basic.assertion.Assert;
import com.miner.disco.basic.constants.BasicConst;
import com.miner.disco.quartz.config.TaskExecutorConfig;
import com.miner.disco.quartz.exception.QuartzBuzExceptionCode;
import com.miner.disco.quartz.runnable.orders.OrdersRunnable;
import com.miner.disco.quartz.service.orders.OrdersService;
import java.util.List;
import java.util.concurrent.Executor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * author:linjw Date:2019/1/14 Time:15:13
 */
@Component
@Slf4j
public class OrdersTask {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private OrdersService ordersService;


    @Scheduled(cron = "${server.task.orders_subduction}")
    public void ordersOvertime() {
        TaskExecutorConfig taskExecutorConfig = new TaskExecutorConfig();
        Executor threadPoolTaskExecutor;
        threadPoolTaskExecutor = taskExecutorConfig.getAsyncExecutor();
        try {
            //当前订单集合下标
            String currentIndex = redisTemplate.opsForValue().get(BasicConst.ORDERS_OVERTIME_INDEX);
            Assert.notNull(currentIndex, QuartzBuzExceptionCode.INIT_ORDERS_INDEX_ERROR.getCode());
            String nextIndex=String.valueOf(Integer.parseInt(currentIndex)+1);
            //加入测试订单数据
            redisTemplate.opsForHash().put(BasicConst.ORDERS_OVERTIME_LIST+":"+nextIndex,nextIndex,"1");
            //当前订单集合
            List<Object> idList = redisTemplate.opsForHash().values(BasicConst.ORDERS_OVERTIME_LIST+":"+currentIndex);
            for (Object id : idList) {
                threadPoolTaskExecutor.execute(new OrdersRunnable(ordersService, Long.parseLong(id.toString())));
            }
            //订单集合下标指向下一个集合
            redisTemplate.opsForValue().set(BasicConst.ORDERS_OVERTIME_INDEX,nextIndex);
            //删除当前超时订单集合
            redisTemplate.opsForHash().delete(BasicConst.ORDERS_OVERTIME_LIST,currentIndex);
        }catch (Exception e){
            log.error(e.getMessage(),e);
        }

    }
}

package com.miner.disco.front.component;


import com.miner.disco.basic.constants.BasicConst;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Created by lubycoder@163.com on 2018/4/10.
 */
@Component
public class SerialNoGenerator {

    @Resource(name = "redisTemplate")
    private ValueOperations<String, String> vOps;

    public String generateSerialNo(SerialKey serialKey, boolean prefix, boolean suffix) {
        String datetime = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        String cacheKey = String.format("%s%s_%s", BasicConst.SERIALNO_CACHE_PREFIX, serialKey.name(), datetime);
        long count = vOps.increment(cacheKey, 1L);
        vOps.getOperations().expire(cacheKey, 1L, TimeUnit.SECONDS);
        String contStr = String.format("%0" + serialKey.getSequenceSize() + "d", count);
        return String.format("%s%s%s", serialKey.prefix, datetime, contStr);
    }

    public String generateSerialNo(SerialKey serialKey) {
        return this.generateSerialNo(serialKey, false, false);
    }

    public Long generateSeq(SerialKey serialKey) {
        String datetime = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        String cacheKey = String.format("%s%s_%s", BasicConst.SERIALNO_CACHE_PREFIX, serialKey.name(), datetime);
        Long count = vOps.increment(cacheKey, 1L);
        vOps.getOperations().expire(cacheKey, 1L, TimeUnit.SECONDS);
        return count;
    }

    public enum SerialKey {

        ORDERS_SEQ(4, "O", "");

        int sequenceSize;
        String prefix;
        String suffix;

        SerialKey(int sequenceSize) {
            this.sequenceSize = sequenceSize;
        }

        SerialKey(int sequenceSize, String prefix) {
            this.sequenceSize = sequenceSize;
            this.prefix = prefix;
        }

        SerialKey(int sequenceSize, String prefix, String suffix) {
            this.sequenceSize = sequenceSize;
            this.suffix = suffix;
            this.prefix = prefix;
        }

        public int getSequenceSize() {
            return sequenceSize;
        }

        public String getSequenceSizeToStr() {
            return String.valueOf(sequenceSize);
        }

        public String prefix() {
            return prefix;
        }

        public String suffix() {
            return suffix;
        }
    }


}

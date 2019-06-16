package com.miner.disco.front.service.impl;

import com.miner.disco.front.dao.ShieldMapper;
import com.miner.disco.front.service.ShieldService;
import org.springframework.beans.factory.annotation.Autowired;

public class ShieldServicelmpl implements ShieldService {
    @Autowired
   private ShieldMapper  shieldMapper;

    @Override
    public boolean saveUserId(Long mid, Long sid) {
        return shieldMapper.saveUserId(mid,sid);

    }

    @Override
    public Long[] queryUses(Long mid) {
        return shieldMapper.queryUses(mid);
    }
}

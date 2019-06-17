package com.miner.disco.front.service;

import com.miner.disco.pojo.Shield;

import java.util.List;

public interface ShieldService {

    boolean saveUserId(Long mid,Long sid);

    List<Shield> queryUses(Long mid);
}

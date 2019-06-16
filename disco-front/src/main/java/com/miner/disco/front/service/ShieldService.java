package com.miner.disco.front.service;

public interface ShieldService {

    boolean saveUserId(Long mid,Long sid);

    Long[] queryUses(Long mid);
}

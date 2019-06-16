package com.miner.disco.front.dao;

public interface ShieldMapper  {

    boolean saveUserId(Long mid,Long sid);

    Long[] queryUses(Long mid);
}

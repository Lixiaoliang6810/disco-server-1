package com.miner.disco.front.dao;

import java.util.List;

public interface ShieldMapper  {

    boolean saveUserId(Long mid,Long sid);

    List<Long> queryUses(Long mid);
}

package com.miner.disco.front.service;

import com.miner.disco.front.model.response.VipMemberListResponse;
import com.miner.disco.pojo.Shield;

import java.util.List;

public interface ShieldService {


    List<VipMemberListResponse> screenList(Long currentUserId, List<VipMemberListResponse> vips);

    public List<Shield> getShieldList(Long currentUserId);
}

package com.miner.disco.front.service;

import com.miner.disco.front.model.response.VipMemberListResponse;

import java.util.List;

public interface ShieldService {


    List<VipMemberListResponse> screenList(Long currentUserId, List<VipMemberListResponse> vips);

}

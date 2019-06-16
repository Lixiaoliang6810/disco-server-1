package com.miner.disco.admin.dao.member;

import com.miner.disco.admin.model.request.member.CollectSearchRequest;
import com.miner.disco.admin.model.response.member.CollectListResponse;
import java.util.List;

/**
 * author:linjw Date:2019/1/8 Time:14:23
 */
public interface CollectDao {

    List<CollectListResponse> collectList(CollectSearchRequest search);

    int countCollectList(CollectSearchRequest search);
}

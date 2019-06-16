package com.miner.disco.admin.dao.system;

import com.miner.disco.admin.model.request.system.BannerSearchRequest;
import com.miner.disco.admin.model.response.system.BannerListResponse;
import com.miner.disco.mybatis.support.BasicMapper;
import com.miner.disco.pojo.Banner;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * author:linjw Date:2019/1/7 Time:19:06
 */
public interface BannerDao extends BasicMapper<Banner> {

    List<BannerListResponse> bannerList(@Param("search") BannerSearchRequest search);

    int countBannerList(@Param("search") BannerSearchRequest search);
}

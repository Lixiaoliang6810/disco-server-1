package com.miner.disco.admin.dao.merchant;

import com.miner.disco.admin.model.response.merchant.ClassifyListResponse;
import com.miner.disco.basic.model.request.Pagination;
import com.miner.disco.mybatis.support.BasicMapper;
import com.miner.disco.pojo.Classify;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * author:linjw Date:2019/1/3 Time:15:45
 */
public interface ClassifyDao extends BasicMapper<Classify> {

    List<ClassifyListResponse> classifyList(@Param("page") Pagination page);


    Integer countClassify();

    List<ClassifyListResponse> allClassify();

}

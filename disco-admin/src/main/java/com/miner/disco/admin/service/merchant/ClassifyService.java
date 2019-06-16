package com.miner.disco.admin.service.merchant;

import com.miner.disco.admin.exception.AdminBuzException;
import com.miner.disco.admin.model.request.merchant.ClassifyCreateRequest;
import com.miner.disco.admin.model.request.merchant.ClassifyModifyRequest;
import com.miner.disco.admin.model.response.merchant.ClassifyListResponse;
import com.miner.disco.basic.model.request.Pagination;
import com.miner.disco.basic.model.response.PageResponse;
import com.miner.disco.pojo.Classify;
import java.util.List;

/**
 * Created with IDEA author:linjw Date:2019/1/3 Time:15:41
 */
public interface ClassifyService {

    /**
     * 创建分类
     * @param createRequest
     * @throws AdminBuzException
     */
    void createClassify(ClassifyCreateRequest createRequest) throws AdminBuzException;

    /**
     * 根据主键查询分类详情
     * @param id
     * @return
     * @throws AdminBuzException
     */
    Classify queryClassifyDetail(Long id) throws AdminBuzException;

    /**
     * 修改分类
     * @param modifyRequest
     * @throws AdminBuzException
     */
    void modifyClassify(ClassifyModifyRequest modifyRequest) throws AdminBuzException;

    /**
     * 查询分类列表
     * @param page
     * @return
     * @throws AdminBuzException
     */
    PageResponse queryClassifyList(Pagination page) throws AdminBuzException;

    /**
     * 查询所有分类
     * @return
     * @throws AdminBuzException
     */
    List<ClassifyListResponse> allClassify() throws AdminBuzException;

}

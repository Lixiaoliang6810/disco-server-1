package com.miner.disco.front.model.request;

import com.miner.disco.basic.model.request.Pagination;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Created by lubycoder@163.com 2018/12/24
 */
@Getter
@Setter
public class MembersRequest extends Pagination {

    private static final long serialVersionUID = 8385007231249517502L;

    /**
     * 经度
     */
    private Double longitude;
    /**
     * 纬度
     */
    private Double latitude;
    /**
     * 推荐
     */
    private Integer recommend;
    /**
     * 性别
     */
    private Integer gender;
    /**
     * 距离
     */
    private Integer distance;
    /**
     * 排序
     */
    private Integer sort;

}

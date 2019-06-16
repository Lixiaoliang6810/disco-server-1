package com.miner.disco.admin.model.request.system;

import com.miner.disco.basic.model.request.Pagination;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * author:linjw Date:2019/1/7 Time:19:16
 */
@Getter
@Setter
public class BannerSearchRequest extends Pagination implements Serializable {

    private static final long serialVersionUID = 5667461752107989335L;

    /**
     * 开始时间
     */
    @DateTimeFormat(pattern = "yyyy/MM/dd HH:mm:ss")
    private Date effectStartTime;

    @DateTimeFormat(pattern = "yyyy/MM/dd HH:mm:ss")
    private Date effectEndTime;

    /**
     * 结束时间
     */
    @DateTimeFormat(pattern = "yyyy/MM/dd HH:mm:ss")
    private Date invalidStartTime;

    @DateTimeFormat(pattern = "yyyy/MM/dd HH:mm:ss")
    private Date invalidEndTime;
}

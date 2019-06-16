package com.miner.disco.admin.model.request.system;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * author:linjw Date:2019/1/7 Time:19:29
 */
@Getter
@Setter
public class BannerModifyRequest implements Serializable {

    private static final long serialVersionUID = -7897940889500135397L;

    /**
     * 主键
     */
    @NotNull(message = "广告不存在")
    private Long id;

    /**
     * 图片
     */
    private String image;

    /**
     * 图片URI
     */
    private String link;
    /**
     * 排序序号
     */
    private Integer sort;
    /**
     * 开始时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;
    /**
     * 结束时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;
}

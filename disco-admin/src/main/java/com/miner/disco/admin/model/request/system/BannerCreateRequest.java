package com.miner.disco.admin.model.request.system;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * author:linjw Date:2019/1/7 Time:19:22
 */
@Getter
@Setter
public class BannerCreateRequest implements Serializable {

    private static final long serialVersionUID = -3178673755804346242L;

    /**
     * 图片
     */
    @NotNull(message = "图片不能为空")
    private String image;

    /**
     * 图片URI
     */
    @NotNull(message = "图片链接不能为空")
    private String link;

    @NotNull(message = "序号不能为空")
    private Integer sort;
    /**
     * 开始时间
     */
    @NotNull(message = "生效时间不能为空")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;
    /**
     * 结束时间
     */
    @NotNull(message = "失效时间不能为空")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;
}

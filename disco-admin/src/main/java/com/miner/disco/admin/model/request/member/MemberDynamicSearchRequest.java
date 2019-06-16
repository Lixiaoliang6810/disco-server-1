package com.miner.disco.admin.model.request.member;

import com.miner.disco.basic.model.request.Pagination;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * author:linjw Date:2019/1/8 Time:10:08
 */
@Getter
@Setter
public class MemberDynamicSearchRequest extends Pagination implements Serializable {

    private static final long serialVersionUID = 8404619602591511843L;

    @NotNull(message = "用户不存在")
    private Long memberId;

    private Integer status;

    private SHOW show = SHOW.DATETIME;

    /**
     * 开始或结束 日期
     */
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date startDate;

    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date endDate;

    /**
     * 开始或结束 年
     */
    private Integer startYear;

    private Integer endYear;

    /**
     * 季度
     */
    private Integer year;

    private Integer quarter;

    public enum SHOW{
        YEAR(1),

        QUARTER(2),

        MONTH(3),

        DATETIME(4);
        Integer show;
        SHOW(Integer show){
            this.show = show;
        }
        public Integer getShow(){
            return this.show;
        }
    }

}

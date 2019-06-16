package com.miner.disco.front.model.request;

import com.spatial4j.core.io.GeohashUtils;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Created by lubycoder@163.com 2019/1/3
 */
@Getter
@Setter
public class MemberInfoModifyRequest implements Serializable {

    private static final long serialVersionUID = -8577762885270255751L;

    /**
     * 用户主键
     */
    private Long id;
    /**
     * 昵称
     */
    private String nickname;
    /**
     * 头像
     */
    private String avatar;
    /**
     * 标签
     */
    private String label;
    /**
     * 标语
     */
    private String slogan;
    /**
     * 城市
     */
    private String city;
    /**
     * 性别（1 - 男 2 - 女）
     */
    private Integer gender;
    /**
     * 生日
     */
    private Date birthday;
    /**
     * 体重
     */
    private Integer weight;
    /**
     * 身高
     */
    private Integer height;
    /**
     * 当前城市
     */
    private String currentCity;
    /**
     * 经度
     */
    private Double longitude;
    /**
     * 纬度
     */
    private Double latitude;
    /**
     * GEOHASH
     */
    private String geohash;
    /**
     * 背景图
     */
    private String backgroundImage;

    public String getGeohash() {
        return longitude != null && latitude != null ? GeohashUtils.encodeLatLon(latitude, longitude) : null;
    }
}

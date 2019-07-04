package com.miner.disco.front.model.request;

import com.miner.disco.basic.constants.BasicConst;
import com.miner.disco.basic.model.request.Pagination;
import com.spatial4j.core.io.GeohashUtils;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author Created by lubycoder@163.com 2019/1/8
 */
@Getter
@Setter
public class VipMemberListRequest extends Pagination implements Serializable {

    private static final long serialVersionUID = 796553843563642617L;

    private Integer gender;

    private String nickname;

    private Integer recommend;

    private Integer distance = -1;

    private Double longitude = 0.0;

    private Double latitude = 0.0;

    private String currentCity;

    private String geohash;

    /**
     * 头像
     */
    private String avatar;

    private SORT sort;

    public String getGeohash() {
        if (distance == -1) return BasicConst.EMPTY;
        int precision = 3;
        if (distance >= 1 && distance <= 3) precision = 5;
        if (distance > 3 && distance < 20) precision = 4;
        if (distance >= 20 && distance <= 80) precision = 3;
        return GeohashUtils.encodeLatLon(this.latitude, this.longitude, precision);
    }

    public enum SORT {
        DISTANCE_FAR_TO_NEAR,
        DISTANCE_NEAR_TO_FAR
    }

    public Double getLongitude() {
        return longitude == null ? 0.0 : longitude;
    }

    public Double getLatitude() {
        return latitude == null ? 0.0 : latitude;
    }

}

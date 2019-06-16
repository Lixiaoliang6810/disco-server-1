package com.miner.disco.front.model.request;

import com.miner.disco.basic.constants.BasicConst;
import com.miner.disco.basic.model.request.Pagination;
import com.miner.disco.front.model.response.MemberBankListResponse;
import com.miner.disco.front.model.response.MerchantListResponse;
import com.spatial4j.core.io.GeohashUtils;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Comparator;

/**
 * @author Created by lubycoder@163.com 2019/1/7
 */
@Getter
@Setter
public class MerchantListRequest extends Pagination implements Serializable {

    private static final long serialVersionUID = -7137069244384668732L;

    private String name;

    private Long classifyId;

    private Integer distance = -1;

    private Double longitude = 0.0;

    private Double latitude = 0.0;

    private String currentCity;

    private String geohash;

    private SORT sort = SORT.INTELLIGENT_SORT;

    public String getGeohash() {
        if (distance == -1) return BasicConst.EMPTY;
        int precision = 3;
        if (distance >= 1 && distance <= 3) precision = 5;
        if (distance > 3 && distance < 20) precision = 4;
        if (distance >= 20 && distance <= 80) precision = 3;
        return GeohashUtils.encodeLatLon(this.latitude, this.longitude, precision);
    }

    public enum SORT {
        INTELLIGENT_SORT(Comparator.comparing(MerchantListResponse::getUpdateDate)),                //智能排序
        SHORTEST_DISTANCE(Comparator.comparing(MerchantListResponse::getDistance)),                 //离我最近
        PRAISE_PREFERRED(Comparator.comparing(MerchantListResponse::getScore)),                     //好评优先
        LATEST_RELEASE(Comparator.comparing(MerchantListResponse::getCreateDate)),                  //最新发布
        PRICE_LOW_TO_HIGH(Comparator.comparing(MerchantListResponse::getPercapita)),                //价格由低到高
        PRICE_HIGH_TO_LOW(Comparator.comparing(MerchantListResponse::getPercapita).reversed());     //价格由高到低

        Comparator<MerchantListResponse> comparable;

        SORT(Comparator<MerchantListResponse> comparable) {
            this.comparable = comparable;
        }

        public Comparator<MerchantListResponse> getComparable() {
            return comparable;
        }
    }

    public Double getLongitude() {
        return longitude == null ? 0.0 : longitude;
    }

    public Double getLatitude() {
        return latitude == null ? 0.0 : latitude;
    }

}

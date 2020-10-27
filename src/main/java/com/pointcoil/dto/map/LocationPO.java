package com.pointcoil.dto.map;

import cn.hutool.core.lang.ObjectId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

/**
 * Created by WuShuang on 2019/11/20.
 */

public class LocationPO   {



    @GeoSpatialIndexed
    private double[] loc;



    public LocationPO(){}

    public LocationPO(double x, double y){
        loc = new double[]{x, y};
    }


    public double[] getLoc() {
        return loc;
    }

    public void setLoc(double[] loc) {
        this.loc = loc;
    }

    @Data
    @Document(collection="location")
    @AllArgsConstructor
    public static class TestLocation{
        private List<LocationPO> locationPOS;
        private String id;
    }
}
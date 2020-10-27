package com.pointcoil.mapper;

import java.util.List;
import java.util.Map;

/**
 * Created by WuShuang on 2019/11/13.
 */
public interface HeatMapMapper {
    /**
     * 热力图列表
     *
     * @Author: WuShuang on 2019/11/13  11:15
     * @param: []
     * @return: java.util.List<java.lang.String>
     * @Description:
     */
    List<Map<String,String>> initHeatMap();
}

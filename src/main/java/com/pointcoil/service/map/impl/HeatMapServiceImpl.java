package com.pointcoil.service.map.impl;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonObject;
import com.pointcoil.mapper.HeatMapMapper;
import com.pointcoil.service.map.HeatMapService;
import com.pointcoil.util.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by WuShuang on 2019/11/13.
 */
@Service
@Slf4j
@SuppressWarnings("ALL")
public class HeatMapServiceImpl implements HeatMapService {

    @Autowired
    private HeatMapMapper heatMapMapper;

    /**
     *热力图
     *
     * @Author: WuShuang on 2019/11/13  11:14
     * @param: []
     * @return: java.lang.String
     * @Description:
     */
    @Override
    public String initHeatMap() {
        List<Map<String,String>> strings = heatMapMapper.initHeatMap();
        return ResponseUtil.successToClient(strings);
    }
}

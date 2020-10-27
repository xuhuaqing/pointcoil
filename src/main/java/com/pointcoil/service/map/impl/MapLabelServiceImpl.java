package com.pointcoil.service.map.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.pointcoil.conf.PointCoilProperties;
import com.pointcoil.dto.map.*;
import com.pointcoil.mapper.MapBusinessMapper;
import com.pointcoil.mapper.MapLabelMapper;
import com.pointcoil.service.map.MapLabelService;
import com.pointcoil.util.RedisUtil;
import com.pointcoil.util.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.pointcoil.constant.MsgConstant.MSG_001010;
import static com.pointcoil.constant.MsgConstant.MSG_001017;

/**
 * Created by WuShuang on 2019/11/8.
 */
@Service
@Slf4j
@SuppressWarnings("ALL")
public class MapLabelServiceImpl implements MapLabelService {

    @Autowired
    private MapLabelMapper mapLabelMapper;
    @Autowired
    private MapBusinessMapper mapBusinessMapper;
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private PointCoilProperties pointCoilProperties;

    /**
     *商圈回显名称
     *
     * @Author: WuShuang on 2019/11/11  9:20
     * @param: [mapCommonDTO]
     * @return: java.lang.String
     * @Description:
     */
    @Override
    public String createLabelShowBusinessName(BrandDTO.BrandId mapCommonDTO) {
        List<Map<String,Object>> list = mapLabelMapper.createLabelShowBusinessName(mapCommonDTO);
        for (Map<String, Object> map : list) {
            map.put("type",false);
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("labelName",list);
        return ResponseUtil.successToClient(jsonObject);
    }

    /**
     *标签展示
     *
     * @Author: WuShuang on 2019/11/11  11:30
     * @param: [mapCommonDTO]
     * @return: java.lang.String
     * @Description:
     */
    @Override
    public String showLabelStyle(MapCommonDTO mapCommonDTO) {
        List<LabelStyleDTO> labelStyleDTO = mapLabelMapper.showLabelStyle();
        return ResponseUtil.successToClient(labelStyleDTO);
    }

    /**
     *创建标签
     *
     * @Author: WuShuang on 2019/11/11  13:27
     * @param: [labelDTO]
     * @return: java.lang.String
     * @Description:
     */
    @Override
    public String createLabel(LabelDTO labelDTO) {

        /**
         *查询当前等级 允许创建多少标签
         *
         * @Author: WuShuang on 2019/11/11  13:29
         * @param: [labelDTO]
         * @return: java.lang.String
         * @Description:
         */
        MemberDTO memberDTO = mapBusinessMapper.selectCanCreateBrand(labelDTO.getMemberLevel());

        Integer i =  mapLabelMapper.selectCreateLabelNum(labelDTO);

        if(i>=memberDTO.getLabelNum()){
            return ResponseUtil.errorToClient(MSG_001010);
        }
        Integer p = mapLabelMapper.selectLabelName(labelDTO);
        if(p>0){
            return ResponseUtil.errorMsgToClient(MSG_001017);
        }
        /**
         * 创建标签
         */
        String replace = UUID.randomUUID().toString().replace("-", "");
        mapLabelMapper.createLabel(labelDTO, JSON.toJSONString(labelDTO.getCoordinates()),replace);
        Map<String,String> map =  new HashMap<>();
        map.put("id",replace);
        map.put("labelStyleUrl",mapLabelMapper.selectLabelStyleById(labelDTO.getLabelStyleId()));
        String labelPageInit = pointCoilProperties.getRedis().getLabelPageInit();
        redisUtil.remove(labelPageInit + labelDTO.getBrandId());
        return ResponseUtil.successToClient(map);
    }

    /**
     * 展示标签
     *
     * @Author: WuShuang on 2019/11/11  15:19
     * @param: [showLabel]
     * @return: java.lang.String
     * @Description:
     */
    @Override
    public String showLabel(LabelDTO.ShowLabel showLabel) {
        JSONObject resultJson1 = new JSONObject();
        //如果是直接走redis 拿
        if(showLabel.getIsAll() == 0){
            String labelPageInit = pointCoilProperties.getRedis().getLabelPageInit();
            Object o = redisUtil.get(labelPageInit + showLabel.getBrandId());
            if(o!=null){
                JSONObject resultJson = (JSONObject) JSONObject.parse(o.toString());
                return ResponseUtil.successToClient(resultJson);
            }
            //如果没有
           List<LabelDTO.ShowLabelDTO> showLabelDTOS = mapLabelMapper.showLabel(new LabelDTO.ShowLabel());
         /*   for(LabelDTO.ShowLabelDTO s:showLabelDTOS){
                String lngAndLat = s.getLatAndLng();
                BusinessZoneDTO.Coordinate parse = JSONObject.parseObject(lngAndLat,BusinessZoneDTO.Coordinate.class);
                s.setCoordinate(parse);
            }*/
            resultJson1.put("labelAll", showLabelDTOS);

            //redisUtil.set(labelPageInit + showLabel.getBrandId(),resultJson1.toJSONString());

            return ResponseUtil.successToClient(resultJson1);
        }else {
            System.err.println(showLabel.toString());
            List<LabelDTO.ShowLabelDTO> showLabelDTOS = mapLabelMapper.showLabel(showLabel);
            /*for(LabelDTO.ShowLabelDTO s:showLabelDTOS){
                String lngAndLat = s.getLatAndLng();
                BusinessZoneDTO.Coordinate parse = JSONObject.parseObject(lngAndLat,BusinessZoneDTO.Coordinate.class);
                s.setCoordinate(parse);
            }*/
            resultJson1.put("labelAll", showLabelDTOS);
            return ResponseUtil.successToClient(resultJson1);
        }
        //
  /*
        return null;*/
    }

    /**
     *删除标签
     *
     * @Author: WuShuang on 2019/11/11  17:29
     * @param: [labelId]
     * @return: java.lang.String
     * @Description:
     */
    @Override
    public String deleteLabel(LabelDTO.LabelId labelId) {
        mapLabelMapper.deleteLabel(labelId);
        return ResponseUtil.successToClient();
    }

    /**
     * 修改回显数据
     *
     * @Author: WuShuang on 2019/11/11  17:34
     * @param: [labelId]
     * @return: java.lang.String
     * @Description:
     */
    @Override
    public String selectLabelById(LabelDTO.LabelId labelId) {
        LabelDTO.SelectLabelById selectLabel = mapLabelMapper.selectLabelById(labelId);
        return ResponseUtil.successToClient(selectLabel);
    }

    /**
     * 修改标签
     *
     * @Author: WuShuang on 2019/11/11  17:57
     * @param: [updateLabel]
     * @return: java.lang.String
     * @Description:
     */
    @Override
    public String updateLabelById(LabelDTO.UpdateLabel updateLabel) {
        mapLabelMapper.updateLabelById(updateLabel);
        return ResponseUtil.successToClient();
    }

    @Override
    public String clear(LabelDTO labelDTO) {

        String replace = UUID.randomUUID().toString().replace("-", "");
        mapLabelMapper.createLabel(labelDTO, JSON.toJSONString(labelDTO.getCoordinates()),replace);
        Map<String,String> map =  new HashMap<>();
        map.put("labelId",replace);
        map.put("labelStyleUrl",mapLabelMapper.selectLabelStyleById(labelDTO.getLabelStyleId()));
       // mapLabelMapper.clear();
        return ResponseUtil.successToClient(map);
    }


}
